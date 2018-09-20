/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author maver
 */
@Entity
@Table(name = "Post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p"),
    @NamedQuery(name = "Post.findByPostId", query = "SELECT p FROM Post p WHERE p.postPK.postId = :postId"),
    @NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post p WHERE p.title = :title"),
    @NamedQuery(name = "Post.findByPubTime", query = "SELECT p FROM Post p WHERE p.pubTime = :pubTime"),
    @NamedQuery(name = "Post.findByContent", query = "SELECT p FROM Post p WHERE p.content = :content"),
    @NamedQuery(name = "Post.findByAccountusername", query = "SELECT p FROM Post p WHERE p.postPK.accountusername = :accountusername ORDER BY p.pubTime DESC"),
    @NamedQuery(name = "Post.findOrderByPubTimeDesc",query = "SELECT p from Post p ORDER BY p.pubTime DESC")})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PostPK postPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "title")
    private String title;
    @Column(name = "pubTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "content")
    private String content;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private Collection<Comment> commentCollection;
    @JoinColumn(name = "Account_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;

    public Post() {
    }

    public Post(PostPK postPK) {
        this.postPK = postPK;
    }

    public Post(PostPK postPK, String title, String content) {
        this.postPK = postPK;
        this.title = title;
        this.content = content;
    }

    public Post(int postId, String accountusername) {
        this.postPK = new PostPK(postId, accountusername);
    }

    public PostPK getPostPK() {
        return postPK;
    }

    public void setPostPK(PostPK postPK) {
        this.postPK = postPK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postPK != null ? postPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.postPK == null && other.postPK != null) || (this.postPK != null && !this.postPK.equals(other.postPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Post[ postPK=" + postPK + " ]";
    }
    
}
