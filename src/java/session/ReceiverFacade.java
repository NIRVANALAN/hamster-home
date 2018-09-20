/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Receiver;
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
public class ReceiverFacade extends AbstractFacade<Receiver> {

    @PersistenceContext(unitName = "tttttPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceiverFacade() {
        super(Receiver.class);
    }

    public Receiver getByUserName(String username) {
        Query query = em.createNamedQuery("Receiver.findByAccountusername");
        query.setParameter("accountusername", username);
        List<Receiver> result = query.getResultList();
        System.out.println(result.size());
        return result.get(0);
    }
    
}
