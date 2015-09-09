/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Controller;

import BusinessLogic.Controller.exceptions.IllegalOrphanException;
import BusinessLogic.Controller.exceptions.NonexistentEntityException;
import BusinessLogic.Controller.exceptions.PreexistingEntityException;
import BusinessLogic.Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DataAccess.Entity.Person;
import DataAccess.Entity.Benefit;
import DataAccess.Entity.Home;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mac
 */
public class HomeJpaController implements Serializable {

    public HomeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Home home) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (home.getBenefitList() == null) {
            home.setBenefitList(new ArrayList<Benefit>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person idperson = home.getIdperson();
            if (idperson != null) {
                idperson = em.getReference(idperson.getClass(), idperson.getIdperson());
                home.setIdperson(idperson);
            }
            List<Benefit> attachedBenefitList = new ArrayList<Benefit>();
            for (Benefit benefitListBenefitToAttach : home.getBenefitList()) {
                benefitListBenefitToAttach = em.getReference(benefitListBenefitToAttach.getClass(), benefitListBenefitToAttach.getBenefitPK());
                attachedBenefitList.add(benefitListBenefitToAttach);
            }
            home.setBenefitList(attachedBenefitList);
            em.persist(home);
            if (idperson != null) {
                idperson.getHomeList().add(home);
                idperson = em.merge(idperson);
            }
            for (Benefit benefitListBenefit : home.getBenefitList()) {
                Home oldHomeOfBenefitListBenefit = benefitListBenefit.getHome();
                benefitListBenefit.setHome(home);
                benefitListBenefit = em.merge(benefitListBenefit);
                if (oldHomeOfBenefitListBenefit != null) {
                    oldHomeOfBenefitListBenefit.getBenefitList().remove(benefitListBenefit);
                    oldHomeOfBenefitListBenefit = em.merge(oldHomeOfBenefitListBenefit);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHome(home.getIdhome()) != null) {
                throw new PreexistingEntityException("Home " + home + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Home home) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Home persistentHome = em.find(Home.class, home.getIdhome());
            Person idpersonOld = persistentHome.getIdperson();
            Person idpersonNew = home.getIdperson();
            List<Benefit> benefitListOld = persistentHome.getBenefitList();
            List<Benefit> benefitListNew = home.getBenefitList();
            List<String> illegalOrphanMessages = null;
            for (Benefit benefitListOldBenefit : benefitListOld) {
                if (!benefitListNew.contains(benefitListOldBenefit)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Benefit " + benefitListOldBenefit + " since its home field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idpersonNew != null) {
                idpersonNew = em.getReference(idpersonNew.getClass(), idpersonNew.getIdperson());
                home.setIdperson(idpersonNew);
            }
            List<Benefit> attachedBenefitListNew = new ArrayList<Benefit>();
            for (Benefit benefitListNewBenefitToAttach : benefitListNew) {
                benefitListNewBenefitToAttach = em.getReference(benefitListNewBenefitToAttach.getClass(), benefitListNewBenefitToAttach.getBenefitPK());
                attachedBenefitListNew.add(benefitListNewBenefitToAttach);
            }
            benefitListNew = attachedBenefitListNew;
            home.setBenefitList(benefitListNew);
            home = em.merge(home);
            if (idpersonOld != null && !idpersonOld.equals(idpersonNew)) {
                idpersonOld.getHomeList().remove(home);
                idpersonOld = em.merge(idpersonOld);
            }
            if (idpersonNew != null && !idpersonNew.equals(idpersonOld)) {
                idpersonNew.getHomeList().add(home);
                idpersonNew = em.merge(idpersonNew);
            }
            for (Benefit benefitListNewBenefit : benefitListNew) {
                if (!benefitListOld.contains(benefitListNewBenefit)) {
                    Home oldHomeOfBenefitListNewBenefit = benefitListNewBenefit.getHome();
                    benefitListNewBenefit.setHome(home);
                    benefitListNewBenefit = em.merge(benefitListNewBenefit);
                    if (oldHomeOfBenefitListNewBenefit != null && !oldHomeOfBenefitListNewBenefit.equals(home)) {
                        oldHomeOfBenefitListNewBenefit.getBenefitList().remove(benefitListNewBenefit);
                        oldHomeOfBenefitListNewBenefit = em.merge(oldHomeOfBenefitListNewBenefit);
                    }
                }
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
                Long id = home.getIdhome();
                if (findHome(id) == null) {
                    throw new NonexistentEntityException("The home with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Home home;
            try {
                home = em.getReference(Home.class, id);
                home.getIdhome();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The home with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Benefit> benefitListOrphanCheck = home.getBenefitList();
            for (Benefit benefitListOrphanCheckBenefit : benefitListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Home (" + home + ") cannot be destroyed since the Benefit " + benefitListOrphanCheckBenefit + " in its benefitList field has a non-nullable home field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Person idperson = home.getIdperson();
            if (idperson != null) {
                idperson.getHomeList().remove(home);
                idperson = em.merge(idperson);
            }
            em.remove(home);
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

    public List<Home> findHomeEntities() {
        return findHomeEntities(true, -1, -1);
    }

    public List<Home> findHomeEntities(int maxResults, int firstResult) {
        return findHomeEntities(false, maxResults, firstResult);
    }

    private List<Home> findHomeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Home.class));
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

    public Home findHome(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Home.class, id);
        } finally {
            em.close();
        }
    }

    public int getHomeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Home> rt = cq.from(Home.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
