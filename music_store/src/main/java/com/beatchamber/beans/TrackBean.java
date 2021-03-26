
package com.beatchamber.beans;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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
    
    @Inject
    private TracksJpaController tracksController; 
    
    public TrackBean(){
        
    }
    
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
    
    /**
     * Method sends the url to the track
     * page for a specific song on an album
     * @return the url for the track page
     */
    public String sendTrack(){
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.trackId = Integer.parseInt(params.get("trackId"));
        this.trackTitle = tracksController.findTracks(trackId).getTrackTitle();
        
        return "track.xhtml";
        
    }
    
    /**
     * Method sends the url to the track
     * page for the first song on the album
     * 
     * Method is used as a default for when a
     * user gets to the track page for the first
     * time
     * @return url to the track page
     */
    public String sendDefaultTrack(){
        
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        int albumId = Integer.parseInt(params.get("albumId"));
        this.trackId = tracksController.findTracksByAlbum(albumId).size();
        this.trackTitle = tracksController.findTracks(trackId).getTrackTitle();
        
        System.out.println("TRACK TITLE: " + trackTitle);

        return "track.xhtml";
    }
    
}
