/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager.report;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Clients;
import com.beatchamber.entities.Orders;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.ClientsJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Yan Tang
 */
@Named("TotalSales")
@ViewScoped
public class TotalSalesBackingBean implements Serializable {

    private int albumId;
    private int trackId;
    private Date saleStartDate;
    private Date saleEndDate;
    private List<OrderAlbumInfo> orderalbumsInfos;
    private List<OrderTrackInfo> ordertracksInfos;
    private List<OrderAlbumInfo> totalSalesInfos = new ArrayList<>();
    private final String[] category = {"Album", "Track"};

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    AlbumsJpaController albumsJpaController;

    /**
     * Get the album id
     *
     * @return
     */
    public int getAlbumId() {
        return this.albumId;
    }

    /**
     * Set the album id
     *
     * @param id
     */
    public void setAlbumId(int id) {
        this.albumId = id;
    }

    /**
     * Get the start date of the search
     *
     * @return
     */
    public Date getSaleStartDate() {
        return this.saleStartDate;
    }

    /**
     * Set the start date of the search
     *
     * @param date
     */
    public void setSaleStartDate(Date date) {
        this.saleStartDate = date;
    }

    /**
     * Get the end date of the search
     *
     * @return
     */
    public Date getSaleEndDate() {
        return saleEndDate;
    }

    /**
     * Set the end date of the search
     *
     * @param date
     */
    public void setSaleEndDate(Date date) {
        this.saleEndDate = date;
    }

    public String[] getCategory() {
        return category;
    }

    /**
     * get TotalSales of the albums
     *
     * @return the client info
     */
    public List<OrderAlbumInfo> getTotalSalesInfos() {
        return this.totalSalesInfos;
    }

    /**
     * Retrieve all the client orders.
     */
    public void retrieveAllOrders() {
        totalSalesInfos = new ArrayList<>();

        List<Albums> albums = this.albumsJpaController.findAlbumsEntities();
        for (Albums album : albums) {
            int id = album.getAlbumNumber();
            String albumtitle = album.getAlbumTitle();
            this.getAllAlbumsByID(saleStartDate, saleEndDate, id);
            this.getTotalSales(id, albumtitle);
        }

    }

    /**
     * Get all the orders by using the album id.
     *
     * @param saleStartDate the start date
     * @param saleEndDate the end date
     * @param clientID the client id
     */
    private void getAllAlbumsByID(Date saleStartDate, Date saleEndDate, int AlbumNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderAlbumInfo> cq = cb.createQuery(OrderAlbumInfo.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Orders> orders = cq.from(Orders.class);
        Join albums = orders.join("orderId");
        if (saleStartDate != null && saleEndDate != null) {
            predicates.add(cb.between(orders.get("orderDate"), this.saleStartDate, this.saleEndDate));
        }
        predicates.add(cb.equal(albums.get("albumNumber"), AlbumNumber));
        cq.where(predicates.toArray(new Predicate[]{}));
        try {
            cq.select(cb.construct(OrderAlbumInfo.class,
                    albums.get("albumNumber"),
                    albums.get("albumTitle"),
                    albums.get("listPrice"),
                    albums.get("salePrice")
            ));
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TotalSalesBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        final TypedQuery<OrderAlbumInfo> typedQuery = entityManager.createQuery(cq);
        List<OrderAlbumInfo> value = typedQuery.getResultList();
        this.orderalbumsInfos = value;
    }

    /**
     * Get the total value of the order
     *
     * @param id
     * @param username
     */
    private double getTotalSales(int id, String albumtitle) {
        double amount = 0;
        for (OrderAlbumInfo orderalbumInfo : this.orderalbumsInfos) {
            if (orderalbumInfo.getSalePrice() != 0) {
                amount += orderalbumInfo.getSalePrice();
            } else {
                amount += orderalbumInfo.getListPrice();
            }
        }
        return amount;
    }
}
