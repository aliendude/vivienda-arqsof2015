/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.DAO;

import DataAccess.Entity.Home;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author trossky
 */
@Stateless
public class HomeFacade extends AbstractFacade<Home> {
    @PersistenceContext(unitName = "HOME_FINALPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HomeFacade() {
        super(Home.class);
    }
    
}
