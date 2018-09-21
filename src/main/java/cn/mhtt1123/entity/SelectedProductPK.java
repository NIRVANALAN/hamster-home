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
public class SelectedProductPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Product_productId")
    private String productproductId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Account_username")
    private String accountusername;

    public SelectedProductPK() {
    }

    public SelectedProductPK(String productproductId, String accountusername) {
        this.productproductId = productproductId;
        this.accountusername = accountusername;
    }

    public String getProductproductId() {
        return productproductId;
    }

    public void setProductproductId(String productproductId) {
        this.productproductId = productproductId;
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
        hash += (productproductId != null ? productproductId.hashCode() : 0);
        hash += (accountusername != null ? accountusername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SelectedProductPK)) {
            return false;
        }
        SelectedProductPK other = (SelectedProductPK) object;
        if ((this.productproductId == null && other.productproductId != null) || (this.productproductId != null && !this.productproductId.equals(other.productproductId))) {
            return false;
        }
        if ((this.accountusername == null && other.accountusername != null) || (this.accountusername != null && !this.accountusername.equals(other.accountusername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.mhtt1123.entity.SelectedProductPK[ productproductId=" + productproductId + ", accountusername=" + accountusername + " ]";
    }

}
