/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.session;

import cn.mhtt1123.entity.SelectedProduct;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author newcoderlife
 */
@Stateless
public class SelectedProductFacade extends AbstractFacade<SelectedProduct> {

    @PersistenceContext(unitName = "cn.mhtt1123_hamster-home_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public SelectedProductFacade() {
        super(SelectedProduct.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public int getTotalOfUser(String username) {
        int sum = 0;
        Query query = em.createNamedQuery("SelectedProduct.findByAccountusername");
        query.setParameter("accountusername", username);
        List<SelectedProduct> result = query.getResultList();
        for (SelectedProduct r : result)
            sum += r.getProductNum() * r.getProduct().getProductPrice();
        return sum;
    }

}
