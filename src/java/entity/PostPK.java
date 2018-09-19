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
public class PostPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "PostId")
    private int postId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Account_username")
    private String accountusername;

    public PostPK() {
    }

    public PostPK(int postId, String accountusername) {
        this.postId = postId;
        this.accountusername = accountusername;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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
        hash += (int) postId;
        hash += (accountusername != null ? accountusername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PostPK)) {
            return false;
        }
        PostPK other = (PostPK) object;
        if (this.postId != other.postId) {
            return false;
        }
        if ((this.accountusername == null && other.accountusername != null) || (this.accountusername != null && !this.accountusername.equals(other.accountusername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PostPK[ postId=" + postId + ", accountusername=" + accountusername + " ]";
    }
    
}
