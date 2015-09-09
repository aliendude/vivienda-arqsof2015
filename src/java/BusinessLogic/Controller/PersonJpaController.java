/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Controller;

import BusinessLogic.Controller.exceptions.NonexistentEntityException;
import BusinessLogic.Controller.exceptions.PreexistingEntityException;
import BusinessLogic.Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DataAccess.Entity.Home;
import DataAccess.Entity.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author mac
 */
public class PersonJpaController implements Serializable {

    public PersonJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Person person) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (person.getHomeList() == null) {
            person.setHomeList(new ArrayList<Home>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Home> attachedHomeList = new ArrayList<Home>();
            for (Home homeListHomeToAttach : person.getHomeList()) {
                homeListHomeToAttach = em.getReference(homeListHomeToAttach.getClass(), homeListHomeToAttach.getIdhome());
                attachedHomeList.add(homeListHomeToAttach);
            }
            person.setHomeList(attachedHomeList);
            em.persist(person);
            for (Home homeListHome : person.getHomeList()) {
                Person oldIdpersonOfHomeListHome = homeListHome.getIdperson();
                homeListHome.setIdperson(person);
                homeListHome = em.merge(homeListHome);
                if (oldIdpersonOfHomeListHome != null) {
                    oldIdpersonOfHomeListHome.getHomeList().remove(homeListHome);
                    oldIdpersonOfHomeListHome = em.merge(oldIdpersonOfHomeListHome);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPerson(person.getIdperson()) != null) {
                throw new PreexistingEntityException("Person " + person + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Person person) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person persistentPerson = em.find(Person.class, person.getIdperson());
            List<Home> homeListOld = persistentPerson.getHomeList();
            List<Home> homeListNew = person.getHomeList();
            List<Home> attachedHomeListNew = new ArrayList<Home>();
            for (Home homeListNewHomeToAttach : homeListNew) {
                homeListNewHomeToAttach = em.getReference(homeListNewHomeToAttach.getClass(), homeListNewHomeToAttach.getIdhome());
                attachedHomeListNew.add(homeListNewHomeToAttach);
            }
            homeListNew = attachedHomeListNew;
            person.setHomeList(homeListNew);
            person = em.merge(person);
            for (Home homeListOldHome : homeListOld) {
                if (!homeListNew.contains(homeListOldHome)) {
                    homeListOldHome.setIdperson(null);
                    homeListOldHome = em.merge(homeListOldHome);
                }
            }
            for (Home homeListNewHome : homeListNew) {
                if (!homeListOld.contains(homeListNewHome)) {
                    Person oldIdpersonOfHomeListNewHome = homeListNewHome.getIdperson();
                    homeListNewHome.setIdperson(person);
                    homeListNewHome = em.merge(homeListNewHome);
                    if (oldIdpersonOfHomeListNewHome != null && !oldIdpersonOfHomeListNewHome.equals(person)) {
                        oldIdpersonOfHomeListNewHome.getHomeList().remove(homeListNewHome);
                        oldIdpersonOfHomeListNewHome = em.merge(oldIdpersonOfHomeListNewHome);
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
                Long id = person.getIdperson();
                if (findPerson(id) == null) {
                    throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getIdperson();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
            }
            List<Home> homeList = person.getHomeList();
            for (Home homeListHome : homeList) {
                homeListHome.setIdperson(null);
                homeListHome = em.merge(homeListHome);
            }
            em.remove(person);
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

    public List<Person> findPersonEntities() {
        return findPersonEntities(true, -1, -1);
    }

    public List<Person> findPersonEntities(int maxResults, int firstResult) {
        return findPersonEntities(false, maxResults, firstResult);
    }

    private List<Person> findPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
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

    public Person findPerson(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Person> rt = cq.from(Person.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
