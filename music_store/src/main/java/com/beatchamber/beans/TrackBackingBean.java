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

    private String title;
    private List<Artists> artists;
    private String coverPath;
    
    @Inject
    AlbumsJpaController albumController;
    
    @Inject
    TracksJpaController trackController;

    public TrackBackingBean() {

    }

    public TrackBackingBean(Tracks track) {

        this.title = track.getTrackTitle();
        coverPath = albumController.getAlbumPath(track.getAlbumNumber().getAlbumNumber(), true);
        artists = trackController.findArtists(track.getTrackId());
    }

    @Override
    public String getTitle() {

        return title;
    }

   @Override
    public List<Artists> getArtists() {

        return artists;
    }

    public void setArtists(List<Artists> artists) {
        this.artists = artists;
    }

    @Override
    public String getCoverPath() {

        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

}
