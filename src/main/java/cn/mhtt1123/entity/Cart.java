/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author newcoderlife
 */
@Entity
@Table(name = "Cart")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c")
        , @NamedQuery(name = "Cart.findById", query = "SELECT c FROM Cart c WHERE c.id = :id")
        , @NamedQuery(name = "Cart.findByQuantity", query = "SELECT c FROM Cart c WHERE c.quantity = :quantity")})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @JoinColumn(name = "cartOwner", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account cartOwner;
    @JoinColumn(name = "cartProduct", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product cartProduct;

    public Cart() {
    }

    public Cart(Integer id) {
        this.id = id;
    }

    public Cart(Integer id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Account getCartOwner() {
        return cartOwner;
    }

    public void setCartOwner(Account cartOwner) {
        this.cartOwner = cartOwner;
    }

    public Product getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(Product cartProduct) {
        this.cartProduct = cartProduct;
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
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.mhtt1123.entity.Cart[ id=" + id + " ]";
    }

}
