/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author newcoderlife
 */
@Embeddable
public class ReceiverPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "phoneno")
    private String phoneno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Account_username")
    private String accountusername;

    public ReceiverPK() {
    }

    public ReceiverPK(String phoneno, String address, String accountusername) {
        this.phoneno = phoneno;
        this.address = address;
        this.accountusername = accountusername;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        hash += (phoneno != null ? phoneno.hashCode() : 0);
        hash += (address != null ? address.hashCode() : 0);
        hash += (accountusername != null ? accountusername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceiverPK)) {
            return false;
        }
        ReceiverPK other = (ReceiverPK) object;
        if ((this.phoneno == null && other.phoneno != null) || (this.phoneno != null && !this.phoneno.equals(other.phoneno))) {
            return false;
        }
        if ((this.address == null && other.address != null) || (this.address != null && !this.address.equals(other.address))) {
            return false;
        }
        if ((this.accountusername == null && other.accountusername != null) || (this.accountusername != null && !this.accountusername.equals(other.accountusername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.mhtt1123.entity.ReceiverPK[ phoneno=" + phoneno + ", address=" + address + ", accountusername=" + accountusername + " ]";
    }

}
