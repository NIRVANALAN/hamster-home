/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maver
 */
@Entity
@Table(name = "selectedProduct")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SelectedProduct.findAll", query = "SELECT s FROM SelectedProduct s"),
    @NamedQuery(name = "SelectedProduct.findByProductproductId", query = "SELECT s FROM SelectedProduct s WHERE s.selectedProductPK.productproductId = :productproductId"),
    @NamedQuery(name = "SelectedProduct.findByProductNum", query = "SELECT s FROM SelectedProduct s WHERE s.productNum = :productNum"),
    @NamedQuery(name = "SelectedProduct.findByAccountusername", query = "SELECT s FROM SelectedProduct s WHERE s.selectedProductPK.accountusername = :accountusername")})
public class SelectedProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SelectedProductPK selectedProductPK;
    @Column(name = "productNum")
    private Integer productNum;
    @JoinColumn(name = "Account_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;
    @JoinColumn(name = "Product_productId", referencedColumnName = "productId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public SelectedProduct() {
    }

    public SelectedProduct(SelectedProductPK selectedProductPK) {
        this.selectedProductPK = selectedProductPK;
    }

    public SelectedProduct(String productproductId, String accountusername) {
        this.selectedProductPK = new SelectedProductPK(productproductId, accountusername);
    }

    public SelectedProductPK getSelectedProductPK() {
        return selectedProductPK;
    }

    public void setSelectedProductPK(SelectedProductPK selectedProductPK) {
        this.selectedProductPK = selectedProductPK;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (selectedProductPK != null ? selectedProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SelectedProduct)) {
            return false;
        }
        SelectedProduct other = (SelectedProduct) object;
        if ((this.selectedProductPK == null && other.selectedProductPK != null) || (this.selectedProductPK != null && !this.selectedProductPK.equals(other.selectedProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SelectedProduct[ selectedProductPK=" + selectedProductPK + " ]";
    }
    
}
