/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.eneity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author newcoderlife
 */
@Entity
@Table(name = "Comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")
    , @NamedQuery(name = "Comment.findByPostPostId", query = "SELECT c FROM Comment c WHERE c.commentPK.postPostId = :postPostId")
    , @NamedQuery(name = "Comment.findByPostAccountusername", query = "SELECT c FROM Comment c WHERE c.commentPK.postAccountusername = :postAccountusername")
    , @NamedQuery(name = "Comment.findByContent", query = "SELECT c FROM Comment c WHERE c.content = :content")
    , @NamedQuery(name = "Comment.findByCommentTime", query = "SELECT c FROM Comment c WHERE c.commentTime = :commentTime")
    , @NamedQuery(name = "Comment.findByAccountusername", query = "SELECT c FROM Comment c WHERE c.commentPK.accountusername = :accountusername")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CommentPK commentPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "content")
    private String content;
    @Column(name = "commentTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentTime;
    @JoinColumn(name = "Account_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;
    @JoinColumns({
        @JoinColumn(name = "Post_PostId", referencedColumnName = "PostId", insertable = false, updatable = false)
        , @JoinColumn(name = "Post_Account_username", referencedColumnName = "Account_username", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Post post;

    public Comment() {
    }

    public Comment(CommentPK commentPK) {
        this.commentPK = commentPK;
    }

    public Comment(CommentPK commentPK, String content) {
        this.commentPK = commentPK;
        this.content = content;
    }

    public Comment(int postPostId, String postAccountusername, String accountusername) {
        this.commentPK = new CommentPK(postPostId, postAccountusername, accountusername);
    }

    public CommentPK getCommentPK() {
        return commentPK;
    }

    public void setCommentPK(CommentPK commentPK) {
        this.commentPK = commentPK;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentPK != null ? commentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentPK == null && other.commentPK != null) || (this.commentPK != null && !this.commentPK.equals(other.commentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.mhtt1123.eneity.Comment[ commentPK=" + commentPK + " ]";
    }
    
}
