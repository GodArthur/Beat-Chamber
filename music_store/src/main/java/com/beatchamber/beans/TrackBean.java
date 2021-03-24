
package com.beatchamber.beans;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Korjon Chang-Jones
 */

@Named("tracks")
@SessionScoped
public class TrackBean implements Serializable{
    
    private final static Logger LOG = LoggerFactory.getLogger(TrackBean.class);
    
    private int trackId;
    private String trackTitle;
    private String musicCategory;
    
    public TrackBean(int trackId, String trackTitle, String musicCategory){
        this.trackId = trackId;
        this.trackTitle = trackTitle;
        this.musicCategory = musicCategory;
    }

    public int getTrackId() {
        return trackId;
    }
    
    public void setTrackId(Integer trackId){
        this.trackId = trackId;
    }
    
    public String getTrackTitle() {
        return trackTitle;
    }
    
    public void setTrackTitle(String title){
        this.trackTitle = title;
    }

    public String getMusicCategory() {
        return musicCategory;
    }
    
    public void setMusicCategory(String musicCat){
        this.musicCategory = musicCat;
    }
    
}
