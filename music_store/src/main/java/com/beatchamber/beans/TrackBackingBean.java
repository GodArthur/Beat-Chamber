package com.beatchamber.beans;

import com.beatchamber.entities.Artists;
import com.beatchamber.entities.Tracks;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *Class is a wrapper for a Track entity.
 * @author Korjon Chang-jones
 */
@Named("trackWrapper")
@SessionScoped
public class TrackBackingBean implements Serializable, MusicComponent {

    private Tracks track;
    private List<Artists> artists;
    private String coverPath;
    
    @Inject
    TrackBean trackBean;
    
    @Inject
    AlbumsJpaController albumController;
    
    @Inject
    TracksJpaController trackController;

    public TrackBackingBean() {

    }

    public TrackBackingBean(Tracks track) {

        this.track = track;
    }

    @Override
    public String getTitle() {

        return track.getTrackTitle();
    }

   @Override
    public List<Artists> getArtists() {

        return artists;
    }

    @Override
    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }
    
    @Override
    public void setCoverPath(String coverPath){
        
        this.coverPath = coverPath;
    }

    @Override
    public String getCoverPath() {

        return coverPath;
    }

    
    @Override
    public String sendComponentPage(){
        
        return trackBean.sendTrack(track);
    }

}
