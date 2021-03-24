package com.beatchamber.beans;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Date;

/**
 *This bean will be used to obtain information for sending a review to the database
 * and it will be used to keep track of what track the client is reviewing.
 * 
 * @author Massimo Di Girolamo
 */
public class CustomerReviewBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(CustomerReviewBean.class);
    
    private int track_id;
    private int client_number;
    private Date review_date;
    private String rating;
    private String review_text;
    private boolean approval_status;
    
    public CustomerReviewBean(int track_id, int client_number, Date review_date, String rating, String review_text, boolean approval_status){
        
        this.track_id = track_id;
        this.client_number = client_number;
        this.review_date = review_date;
        this.rating = rating;
        this.review_text = review_text;
        this.approval_status = approval_status;
    }
    
    public int getTrack_id(){
        return this.track_id;
    }
    
    public void setTrack_id(int trackId){
        this.track_id = trackId;
    }
    
    public int getClient_number(){
        return this.client_number;
    }
    
    public void setClient_number(int clientNumber){
        this.client_number = clientNumber;
    }
    
    public Date getReview_date(){
        return this.review_date;
    }
    
    public void setReview_date(Date reviewDate){
        this.review_date = reviewDate;
    }
    
    public String getRating(){
        return this.rating;
    }
    
    public void setRating(String rating){
        this.rating = rating;
    }
    
    public String getReview_Text(){
        return this.review_text;
    }
    
    public void setReview_Text(String reviewText){
        this.review_text = reviewText;
    }
    
    public boolean getApprovalStatus(){
        return this.approval_status;
    }
    
    public void setApprovalStatus(boolean approvStatus){
        this.approval_status = approvStatus;
    }
}
