/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author kibra
 */
@Entity
@Table(name = "order_track")
@NamedQueries({
    @NamedQuery(name = "OrderTrack.findAll", query = "SELECT o FROM OrderTrack o"),
    @NamedQuery(name = "OrderTrack.findByOrderId", query = "SELECT o FROM OrderTrack o WHERE o.orderId = :orderId")})
public class OrderTrack implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id")
    private Integer orderId;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne(optional = false)
    private Tracks trackId;

    public OrderTrack() {
    }

    public OrderTrack(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Tracks getTrackId() {
        return trackId;
    }

    public void setTrackId(Tracks trackId) {
        this.trackId = trackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderTrack)) {
            return false;
        }
        OrderTrack other = (OrderTrack) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.beatchamber.entities.OrderTrack[ orderId=" + orderId + " ]";
    }
    
}
