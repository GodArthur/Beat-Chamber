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
import javax.validation.constraints.NotNull;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tablekey")
    private Integer tablekey;
    @Basic(optional = false)
    @NotNull
    @Column(name = "order_id")
    private int orderId;

    private static final long serialVersionUID = 1L;
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
    public String toString() {
        return "com.beatchamber.entities.OrderTrack[ orderId=" + orderId + " ]";
    }

    public OrderTrack(Integer tablekey, int orderId) {
        this.tablekey = tablekey;
        this.orderId = orderId;
    }

    public Integer getTablekey() {
        return tablekey;
    }

    public void setTablekey(Integer tablekey) {
        this.tablekey = tablekey;
    }


    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    
}
