/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Controller;

import BusinessLogic.Controller.exceptions.NonexistentEntityException;
import BusinessLogic.Controller.exceptions.PreexistingEntityException;
import BusinessLogic.Controller.exceptions.RollbackFailureException;
import DataAccess.Entity.Benefit;
import DataAccess.Entity.BenefitPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DataAccess.Entity.Home;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mac
 */
public class BenefitJpaController implements Serializable {

    public BenefitJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Benefit benefit) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (benefit.getBenefitPK() == null) {
            benefit.setBenefitPK(new BenefitPK());
        }
        benefit.getBenefitPK().setIdhome(benefit.getHome().getIdhome());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Home home = benefit.getHome();
            if (home != null) {
                home = em.getReference(home.getClass(), home.getIdhome());
                benefit.setHome(home);
            }
            em.persist(benefit);
            if (home != null) {
                home.getBenefitList().add(benefit);
                home = em.merge(home);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBenefit(benefit.getBenefitPK()) != null) {
                throw new PreexistingEntityException("Benefit " + benefit + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Benefit benefit) throws NonexistentEntityException, RollbackFailureException, Exception {
        benefit.getBenefitPK().setIdhome(benefit.getHome().getIdhome());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Benefit persistentBenefit = em.find(Benefit.class, benefit.getBenefitPK());
            Home homeOld = persistentBenefit.getHome();
            Home homeNew = benefit.getHome();
            if (homeNew != null) {
                homeNew = em.getReference(homeNew.getClass(), homeNew.getIdhome());
                benefit.setHome(homeNew);
            }
            benefit = em.merge(benefit);
            if (homeOld != null && !homeOld.equals(homeNew)) {
                homeOld.getBenefitList().remove(benefit);
                homeOld = em.merge(homeOld);
            }
            if (homeNew != null && !homeNew.equals(homeOld)) {
                homeNew.getBenefitList().add(benefit);
                homeNew = em.merge(homeNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BenefitPK id = benefit.getBenefitPK();
                if (findBenefit(id) == null) {
                    throw new NonexistentEntityException("The benefit with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BenefitPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Benefit benefit;
            try {
                benefit = em.getReference(Benefit.class, id);
                benefit.getBenefitPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The benefit with id " + id + " no longer exists.", enfe);
            }
            Home home = benefit.getHome();
            if (home != null) {
                home.getBenefitList().remove(benefit);
                home = em.merge(home);
            }
            em.remove(benefit);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Benefit> findBenefitEntities() {
        return findBenefitEntities(true, -1, -1);
    }

    public List<Benefit> findBenefitEntities(int maxResults, int firstResult) {
        return findBenefitEntities(false, maxResults, firstResult);
    }

    private List<Benefit> findBenefitEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Benefit.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Benefit findBenefit(BenefitPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Benefit.class, id);
        } finally {
            em.close();
        }
    }

    public int getBenefitCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Benefit> rt = cq.from(Benefit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
