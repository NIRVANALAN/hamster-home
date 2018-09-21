/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.session;

import cn.mhtt1123.entity.Receiver;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author newcoderlife
 */
@Stateless
public class ReceiverFacade extends AbstractFacade<Receiver> {

    @PersistenceContext(unitName = "cn.mhtt1123_hamster-home_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ReceiverFacade() {
        super(Receiver.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Receiver getByUserName(String username) {
        Query query = em.createNamedQuery("Receiver.findByAccountusername");
        query.setParameter("accountusername", username);
        List<Receiver> result = query.getResultList();
        System.out.println(result.size());
        return result.get(0);
    }

}
