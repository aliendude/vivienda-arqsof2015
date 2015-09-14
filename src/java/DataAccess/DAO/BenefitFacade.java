/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.DAO;

import DataAccess.Entity.Benefit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author trossky
 */
@Stateless
public class BenefitFacade extends AbstractFacade<Benefit> {
    @PersistenceContext(unitName = "HOME_FINALPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BenefitFacade() {
        super(Benefit.class);
    }
    
}
