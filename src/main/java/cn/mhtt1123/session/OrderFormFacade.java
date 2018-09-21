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

}
