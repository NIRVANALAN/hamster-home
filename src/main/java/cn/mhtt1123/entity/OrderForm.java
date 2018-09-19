/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "OrderForm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderForm.findAll", query = "SELECT o FROM OrderForm o")
    , @NamedQuery(name = "OrderForm.findById", query = "SELECT o FROM OrderForm o WHERE o.id = :id")
    , @NamedQuery(name = "OrderForm.findByAddress", query = "SELECT o FROM OrderForm o WHERE o.address = :address")
    , @NamedQuery(name = "OrderForm.findByQuantity", query = "SELECT o FROM OrderForm o WHERE o.quantity = :quantity")
    , @NamedQuery(name = "OrderForm.findByOrderTime", query = "SELECT o FROM OrderForm o WHERE o.orderTime = :orderTime")})
public class OrderForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orderTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;
    @JoinColumn(name = "orderFormReceiver", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account orderFormReceiver;
    @JoinColumn(name = "orderFormSender", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account orderFormSender;

    public OrderForm() {
    }

    public OrderForm(Integer id) {
        this.id = id;
    }

    public OrderForm(Integer id, String address, int quantity, Date orderTime) {
        this.id = id;
        this.address = address;
        this.quantity = quantity;
        this.orderTime = orderTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Account getOrderFormReceiver() {
        return orderFormReceiver;
    }

    public void setOrderFormReceiver(Account orderFormReceiver) {
        this.orderFormReceiver = orderFormReceiver;
    }

    public Account getOrderFormSender() {
        return orderFormSender;
    }

    public void setOrderFormSender(Account orderFormSender) {
        this.orderFormSender = orderFormSender;
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
        if (!(object instanceof OrderForm)) {
            return false;
        }
        OrderForm other = (OrderForm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.mhtt1123.entity.OrderForm[ id=" + id + " ]";
    }
    
}
