/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.OrderForm;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author maver
 */
@Stateless
public class OrderFormFacade extends AbstractFacade<OrderForm> {

    @PersistenceContext(unitName = "tttttPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderFormFacade() {
        super(OrderForm.class);
    }
    
    public List<OrderForm> getMyOrder(String accountUsername,int [] range){
        Query query = em.createNamedQuery("OrderForm.findByReceiverAccountusername");
        query.setParameter("receiverAccountusername", accountUsername);
        query.setFirstResult(range[0]);
        query.setMaxResults(range[1]-range[0]+1);    
        List<OrderForm> orders =query.getResultList();
        return orders;
    }
    
}
