/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.session;

import cn.mhtt1123.entity.OrderForm;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author newcoderlife
 */
@Stateless
public class OrderFormFacade extends AbstractFacade<OrderForm> {

    @PersistenceContext(unitName = "cn.mhtt1123_hamster-home_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public OrderFormFacade() {
        super(OrderForm.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<OrderForm> getMyOrder(String accountUsername, int[] range) {
        Query query = em.createNamedQuery("OrderForm.findByReceiverAccountusername");
        query.setParameter("receiverAccountusername", accountUsername);
        query.setFirstResult(range[0]);
        query.setMaxResults(range[1] - range[0] + 1);
        List<OrderForm> orders = query.getResultList();
        return orders;
    }

}
