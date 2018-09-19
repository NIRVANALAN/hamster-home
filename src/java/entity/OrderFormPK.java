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
public class OrderFormPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "orderID")
    private String orderID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Receiver_address")
    private String receiveraddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Receiver_phoneno")
    private String receiverphoneno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "Receiver_Account_username")
    private String receiverAccountusername;

    public OrderFormPK() {
    }

    public OrderFormPK(String orderID, String receiveraddress, String receiverphoneno, String receiverAccountusername) {
        this.orderID = orderID;
        this.receiveraddress = receiveraddress;
        this.receiverphoneno = receiverphoneno;
        this.receiverAccountusername = receiverAccountusername;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getReceiveraddress() {
        return receiveraddress;
    }

    public void setReceiveraddress(String receiveraddress) {
        this.receiveraddress = receiveraddress;
    }

    public String getReceiverphoneno() {
        return receiverphoneno;
    }

    public void setReceiverphoneno(String receiverphoneno) {
        this.receiverphoneno = receiverphoneno;
    }

    public String getReceiverAccountusername() {
        return receiverAccountusername;
    }

    public void setReceiverAccountusername(String receiverAccountusername) {
        this.receiverAccountusername = receiverAccountusername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        hash += (receiveraddress != null ? receiveraddress.hashCode() : 0);
        hash += (receiverphoneno != null ? receiverphoneno.hashCode() : 0);
        hash += (receiverAccountusername != null ? receiverAccountusername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderFormPK)) {
            return false;
        }
        OrderFormPK other = (OrderFormPK) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        if ((this.receiveraddress == null && other.receiveraddress != null) || (this.receiveraddress != null && !this.receiveraddress.equals(other.receiveraddress))) {
            return false;
        }
        if ((this.receiverphoneno == null && other.receiverphoneno != null) || (this.receiverphoneno != null && !this.receiverphoneno.equals(other.receiverphoneno))) {
            return false;
        }
        if ((this.receiverAccountusername == null && other.receiverAccountusername != null) || (this.receiverAccountusername != null && !this.receiverAccountusername.equals(other.receiverAccountusername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderFormPK[ orderID=" + orderID + ", receiveraddress=" + receiveraddress + ", receiverphoneno=" + receiverphoneno + ", receiverAccountusername=" + receiverAccountusername + " ]";
    }
    
}
