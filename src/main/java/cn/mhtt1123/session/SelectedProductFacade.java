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

}
