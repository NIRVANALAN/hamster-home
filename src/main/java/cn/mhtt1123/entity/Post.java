/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author newcoderlife
 */
@Entity
@Table(name = "Post")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p")
        , @NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id")
        , @NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post p WHERE p.title = :title")
        , @NamedQuery(name = "Post.findByContent", query = "SELECT p FROM Post p WHERE p.content = :content")
        , @NamedQuery(name = "Post.findByPubTime", query = "SELECT p FROM Post p WHERE p.pubTime = :pubTime")})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "title")
    private String title;
    @Size(max = 45)
    @Column(name = "content")
    private String content;
    @Column(name = "pubTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubTime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "refPost")
    private List<Comment> commentList;
    @JoinColumn(name = "postAuthor", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account postAuthor;

    public Post() {
    }

    public Post(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Account getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(Account postAuthor) {
        this.postAuthor = postAuthor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.mhtt1123.entity.Post[ id=" + id + " ]";
    }

}
