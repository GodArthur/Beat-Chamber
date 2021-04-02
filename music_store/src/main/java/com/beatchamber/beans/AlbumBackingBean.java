package com.beatchamber.beans;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Artists;
import com.beatchamber.entities.Tracks;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Class is a wrapper for an Album entity
 *
 * @author Korjon Chang-Jones
 */
@Named("albumWrapper")
@SessionScoped
public class AlbumBackingBean implements Serializable, MusicComponent {

    private Albums album;
    private List<Artists> artists;
    private String coverPath;

    @Inject
    AlbumsJpaController albumController;

    public AlbumBackingBean() {

        this.album = new Albums();
    }

    public AlbumBackingBean(Albums album) {

        this.album = album;
        coverPath = albumController.getAlbumPath(album.getAlbumNumber(), true);
        artists = albumController.findArtists(album.getAlbumNumber());
    }

    @Override
    public String getTitle() {

        return album.getAlbumTitle();
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
