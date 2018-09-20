/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.mhtt1123.eneity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author newcoderlife
 */
@Entity
@Table(name = "Receiver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receiver.findAll", query = "SELECT r FROM Receiver r")
    , @NamedQuery(name = "Receiver.findByNickname", query = "SELECT r FROM Receiver r WHERE r.nickname = :nickname")
    , @NamedQuery(name = "Receiver.findByPhoneno", query = "SELECT r FROM Receiver r WHERE r.receiverPK.phoneno = :phoneno")
    , @NamedQuery(name = "Receiver.findByAddress", query = "SELECT r FROM Receiver r WHERE r.receiverPK.address = :address")
    , @NamedQuery(name = "Receiver.findByAccountusername", query = "SELECT r FROM Receiver r WHERE r.receiverPK.accountusername = :accountusername")})
public class Receiver implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReceiverPK receiverPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nickname")
    private String nickname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private List<OrderForm> orderFormList;
    @JoinColumn(name = "Account_username", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Account account;

    public Receiver() {
    }

    public Receiver(ReceiverPK receiverPK) {
        this.receiverPK = receiverPK;
    }

    public Receiver(ReceiverPK receiverPK, String nickname) {
        this.receiverPK = receiverPK;
        this.nickname = nickname;
    }

    public Receiver(String phoneno, String address, String accountusername) {
        this.receiverPK = new ReceiverPK(phoneno, address, accountusername);
    }

    public ReceiverPK getReceiverPK() {
        return receiverPK;
    }

    public void setReceiverPK(ReceiverPK receiverPK) {
        this.receiverPK = receiverPK;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @XmlTransient
    public List<OrderForm> getOrderFormList() {
        return orderFormList;
    }

    public void setOrderFormList(List<OrderForm> orderFormList) {
        this.orderFormList = orderFormList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiverPK != null ? receiverPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receiver)) {
            return false;
        }
        Receiver other = (Receiver) object;
        if ((this.receiverPK == null && other.receiverPK != null) || (this.receiverPK != null && !this.receiverPK.equals(other.receiverPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cn.mhtt1123.eneity.Receiver[ receiverPK=" + receiverPK + " ]";
    }
    
}
