/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.session;

import cn.mhtt1123.entity.Post;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author newcoderlife
 */
@Stateless
public class PostFacade extends AbstractFacade<Post> {

    @PersistenceContext(unitName = "cn.mhtt1123_hamster-home_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public PostFacade() {
        super(Post.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Post> findPostsOrderPubTime(int[] range) {
        Query query = em.createNamedQuery("Post.findOrderByPubTimeDesc");
        query.setFirstResult(range[0]);
        query.setMaxResults(range[1] - range[0] + 1);
        return query.getResultList();
    }
}
