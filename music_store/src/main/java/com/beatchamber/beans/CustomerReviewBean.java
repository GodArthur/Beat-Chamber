package com.beatchamber.beans;

import com.beatchamber.beans.manager.SurveysBackingBean;
import com.beatchamber.entities.CustomerReviews;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.CustomerReviewsJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This bean will be used to obtain information for sending a review to the
 * database and it will be used to keep track of what track the client is
 * reviewing.
 *
 * @author Massimo Di Girolamo
 */
@Named("customerReviews")
@RequestScoped
public class CustomerReviewBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(CustomerReviewBean.class);

    @Inject
    private CustomerReviewsJpaController customerReviewsJpaController;

    @Inject
    private TracksJpaController tracksJpaController;


    @Inject
    private LoginRegisterBean userLoginBean;
            
    @Inject
    private TrackBean trackBean;

    private List<CustomerReviews> customerReviews;

    private CustomerReviews selectedCustomerReviews;

    private int track_id;
    private int client_number;
    private Date review_date;
    private int rating;
    private String review_text;
    private boolean approval_status;

    /*@PostConstruct
    public void init() {
        this.customerReviews = customerReviewsJpaController.findCustomerReviewsEntities();
    }*/
    /**
     * Constructor
     */
    public CustomerReviewBean() {
        this.selectedCustomerReviews = new CustomerReviews();
    }

    public int getTrack_id() {
        return this.track_id;
    }

    public void setTrack_id(Integer trackId) {
        this.track_id = trackId;
    }

    public int getClient_number() {
        return this.client_number;
    }

    public void setClient_number(int clientNumber) {
        this.client_number = clientNumber;
    }

    public Date getReview_date() {
        return this.review_date;
    }

    public void setReview_date(Date reviewDate) {
        this.review_date = reviewDate;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview_Text() {
        return this.review_text;
    }

    public void setReview_Text(String reviewText) {
        this.review_text = reviewText;
    }

    public boolean getApprovalStatus() {
        return this.approval_status;
    }

    public void setApprovalStatus(boolean approvStatus) {
        this.approval_status = approvStatus;
    }

    public void addCustomerReview() throws RollbackFailureException {

        try {
            selectedCustomerReviews.setTrackId(tracksJpaController.findTracks(trackBean.getTrackId()));
            selectedCustomerReviews.setClientNumber(userLoginBean.getClient());
            selectedCustomerReviews.setReviewDate(new java.sql.Date(System.currentTimeMillis()));

            //this.rating and this.review should be set by user on site 
            selectedCustomerReviews.setApprovalStatus(false);

            //If the rating and the review are filled in then review can be made
            if (this.rating == 0 || this.review_text.isEmpty()) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("The review must have a rating and a written review"));
            
            } else {
                LOG.trace("Creating new review entity object");
                selectedCustomerReviews.setRating(this.rating);
                selectedCustomerReviews.setReviewText(this.review_text);
                
                customerReviewsJpaController.create(selectedCustomerReviews);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Review Added! It will be shown once it is approved"));

            }

        } catch (RollbackFailureException ex) {
            java.util.logging.Logger.getLogger(SurveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
