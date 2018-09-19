/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author maver
 */
@Embeddable
public class CommentPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Post_PostId")
    private int postPostId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Post_Account_username")
    private String postAccountusername;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Account_username")
    private String accountusername;

    public CommentPK() {
    }

    public CommentPK(int postPostId, String postAccountusername, String accountusername) {
        this.postPostId = postPostId;
        this.postAccountusername = postAccountusername;
        this.accountusername = accountusername;
    }

    public int getPostPostId() {
        return postPostId;
    }

    public void setPostPostId(int postPostId) {
        this.postPostId = postPostId;
    }

    public String getPostAccountusername() {
        return postAccountusername;
    }

    public void setPostAccountusername(String postAccountusername) {
        this.postAccountusername = postAccountusername;
    }

    public String getAccountusername() {
        return accountusername;
    }

    public void setAccountusername(String accountusername) {
        this.accountusername = accountusername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) postPostId;
        hash += (postAccountusername != null ? postAccountusername.hashCode() : 0);
        hash += (accountusername != null ? accountusername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CommentPK)) {
            return false;
        }
        CommentPK other = (CommentPK) object;
        if (this.postPostId != other.postPostId) {
            return false;
        }
        if ((this.postAccountusername == null && other.postAccountusername != null) || (this.postAccountusername != null && !this.postAccountusername.equals(other.postAccountusername))) {
            return false;
        }
        if ((this.accountusername == null && other.accountusername != null) || (this.accountusername != null && !this.accountusername.equals(other.accountusername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CommentPK[ postPostId=" + postPostId + ", postAccountusername=" + postAccountusername + ", accountusername=" + accountusername + " ]";
    }
    
}
