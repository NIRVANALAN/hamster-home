/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "OrderForm")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderForm.findAll", query = "SELECT o FROM OrderForm o"),
    @NamedQuery(name = "OrderForm.findByCreateTime", query = "SELECT o FROM OrderForm o WHERE o.orderFormPK.createTime = :createTime"),
    @NamedQuery(name = "OrderForm.findByFee", query = "SELECT o FROM OrderForm o WHERE o.fee = :fee"),
    @NamedQuery(name = "OrderForm.findByReceiveraddress", query = "SELECT o FROM OrderForm o WHERE o.orderFormPK.receiveraddress = :receiveraddress"),
    @NamedQuery(name = "OrderForm.findByReceiverphoneno", query = "SELECT o FROM OrderForm o WHERE o.orderFormPK.receiverphoneno = :receiverphoneno"),
    @NamedQuery(name = "OrderForm.findByReceiverAccountusername", query = "SELECT o FROM OrderForm o WHERE o.orderFormPK.receiverAccountusername = :receiverAccountusername")})
public class OrderForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderFormPK orderFormPK;
    @Column(name = "fee")
    private BigInteger fee;
    @JoinColumns({
        @JoinColumn(name = "Receiver_address", referencedColumnName = "address", insertable = false, updatable = false),
        @JoinColumn(name = "Receiver_phoneno", referencedColumnName = "phoneno", insertable = false, updatable = false),
        @JoinColumn(name = "Receiver_Account_username", referencedColumnName = "Account_username", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Receiver receiver;

    public OrderForm() {
    }

    public OrderForm(OrderFormPK orderFormPK) {
        this.orderFormPK = orderFormPK;
    }

    public OrderForm(Date createTime, String receiveraddress, String receiverphoneno, String receiverAccountusername) {
        this.orderFormPK = new OrderFormPK(createTime, receiveraddress, receiverphoneno, receiverAccountusername);
    }

    public OrderFormPK getOrderFormPK() {
        return orderFormPK;
    }

    public void setOrderFormPK(OrderFormPK orderFormPK) {
        this.orderFormPK = orderFormPK;
    }

    public BigInteger getFee() {
        return fee;
    }

    public void setFee(BigInteger fee) {
        this.fee = fee;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderFormPK != null ? orderFormPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderForm)) {
            return false;
        }
        OrderForm other = (OrderForm) object;
        if ((this.orderFormPK == null && other.orderFormPK != null) || (this.orderFormPK != null && !this.orderFormPK.equals(other.orderFormPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderForm[ orderFormPK=" + orderFormPK + " ]";
    }
    
}
