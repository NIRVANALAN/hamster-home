/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Post;
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
public class PostFacade extends AbstractFacade<Post> {

    @PersistenceContext(unitName = "tttttPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PostFacade() {
        super(Post.class);
    }
    
    public List<Post> getPostOrderByPubTime(int [] range) {
        Query query = em.createNamedQuery("Post.findOrderByPubTimeDesc");
        query.setFirstResult(range[0]);
        query.setMaxResults(range[1]-range[0]+1);
        List<Post> posts = query.getResultList();
        return posts;
    }
    
}
