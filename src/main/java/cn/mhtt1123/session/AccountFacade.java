/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.session;

import cn.mhtt1123.entity.Account;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author newcoderlife
 */
@Stateless
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "cn.mhtt1123_hamster-home_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Account findByUsername(String username) {
        return (Account) em.createNamedQuery("Account.findByUsername").setParameter("username", username).getSingleResult();
    }
}
