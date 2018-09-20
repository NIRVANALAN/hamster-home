/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.SelectedProduct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 *
 * @author maver
 */
@Stateless
public class SelectedProductFacade extends AbstractFacade<SelectedProduct> {

    @PersistenceContext(unitName = "tttttPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SelectedProductFacade() {
        super(SelectedProduct.class);
    }

    public int getTotalOfUser(String username) {
        int sum = 0;
        Query query = em.createNamedQuery("SelectedProduct.findByAccountusername");
        query.setParameter("accountusername", username);
        List<SelectedProduct> result = query.getResultList();
        for (SelectedProduct r : result) {
            sum+=r.getProductNum()*r.getProduct().getProductPrice();
        }
        return sum;
    }
    
}