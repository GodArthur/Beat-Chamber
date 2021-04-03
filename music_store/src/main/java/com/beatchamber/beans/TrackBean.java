package com.beatchamber.beans;

import com.beatchamber.entities.Tracks;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import java.util.List;
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
public class TrackBean implements Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginRegisterBean.class);

    private int trackId;
    private String trackTitle;
    private String musicCategory;

    @Inject
    private TracksJpaController tracksController;

    @Inject
    private AlbumBean album;

    private List<Tracks> trackList;

    public TrackBean() {

    }

    public TrackBean(int trackId, String trackTitle, String musicCategory) {
        this.trackId = trackId;
        this.trackTitle = trackTitle;
        this.musicCategory = musicCategory;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String title) {
        this.trackTitle = title;
    }

    public String getMusicCategory() {
        return musicCategory;
    }

    public void setMusicCategory(String musicCat) {
        this.musicCategory = musicCat;
    }

    public List<Tracks> getTrackList() {

        return trackList;
    }

    /**
     * Method sends the url to the track page for a specific song on an album
     *
     * @return the url for the track page
     */
    public String sendTrack() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        this.trackId = Integer.parseInt(params.get("trackId"));
        this.trackTitle = tracksController.findTracks(trackId).getTrackTitle();
        storeSimilarAlbums();

        return "track.xhtml";

    }

    /**
     * Method sends url to the track page for a specific song on the browse
     * music page
     *
     * @param track
     * @return
     */
    public String sendTrack(Tracks track) {

        this.trackId = track.getTrackId();
        this.trackTitle = track.getTrackTitle();
        storeSimilarAlbums();

        return "track.xhtml";
    }

    private void storeSimilarAlbums() {

        album.storeSimilarAlbums(tracksController.findGenre(trackId));
    }

    /**
     * Method sends the url to the track page for the first song on the album
     *
     * Used as a default for when a user gets to the track page for
     * the first time
     *
     * @return url to the track page
     */
    public String sendDefaultTrack() {

        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        int albumId = Integer.parseInt(params.get("albumId"));
        trackList = tracksController.findTracksByAlbum(albumId);
        this.trackId = trackList.get(0).getTrackId();
        this.trackTitle = tracksController.findTracks(trackId).getTrackTitle();

        storeSimilarAlbums();
        return "track.xhtml";
    }

}
