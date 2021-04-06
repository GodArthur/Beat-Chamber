/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Artists;
import com.beatchamber.entities.Tracks;
import com.beatchamber.entities.Genres;
import com.beatchamber.entities.GenreToAlbum;
import com.beatchamber.entities.ArtistAlbums;
import com.beatchamber.entities.GenreToTracks;
import com.beatchamber.entities.ArtistsToTracks;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.ArtistAlbumsJpaController;
import com.beatchamber.jpacontroller.ArtistsJpaController;
import com.beatchamber.jpacontroller.ArtistsToTracksJpaController;
import com.beatchamber.jpacontroller.GenreToAlbumJpaController;
import com.beatchamber.jpacontroller.GenreToTracksJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import com.beatchamber.jpacontroller.GenresJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 1733570 Yan Tang
 */
@Named("theAlbums")
@ViewScoped
public class AlbumsBackingBean implements Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(AlbumsBackingBean.class);

    @Inject
    private AlbumsJpaController albumsJpaController;

    @Inject
    private TracksJpaController tracksJpaController;

    @Inject
    private GenresJpaController genresJpaController;

    @Inject
    private ArtistsJpaController artistsJpaController;

    @Inject
    private GenreToAlbumJpaController genreToAlbumJpaController;

    @Inject
    private ArtistAlbumsJpaController artistAlbumsJpaController;

    @Inject
    private GenreToTracksJpaController genreToTracksJpaController;

    @Inject
    private ArtistsToTracksJpaController artistsToTracksJpaController;

    private List<Albums> albums;
    private Albums selectedAlbum;
    private Tracks selectedTrack;
    private List<Tracks> tracksListInAlbum;
    private Tracks newTracks;

    private boolean isAddTrack;
    private boolean isRemoved;

    private Integer[] selectedGenres;
    private Integer[] selectedArtists;
    private Integer[] selectedTrackId;

    List<Genres> albumGenres;

    @PostConstruct
    public void init() {
        this.albums = albumsJpaController.findAlbumsEntities();
    }

    public List<Albums> getAlbums() {
        return albums;
    }

    public Albums getSelectedAlbum() {
        if (this.selectedAlbum == null) {
            this.selectedAlbum = new Albums();
        }
        return selectedAlbum;
    }

    public void setSelectedAlbum(Albums selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }

    public Tracks getSelectedTrack() {
        if (this.selectedTrack == null) {
            this.selectedTrack = new Tracks();
        }
        return selectedTrack;
    }

    public void setSelectedTrack(Tracks selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public Tracks getNewTracks() {
        return newTracks;
    }

    public void setNewTracks(Tracks newTracks) {
        this.newTracks = newTracks;
    }

    public boolean getIsAddTrack() {
        return isAddTrack;
    }

    public void setIsAddTrack(boolean isAddTrack) {
        this.isAddTrack = isAddTrack;
    }

    public boolean getIsRemoved() {
        return this.isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Integer[] getSelectedGenres() {
        return selectedGenres;
    }

    public void setSelectedGenres(Integer[] genreList) {
        this.selectedGenres = genreList;
    }

    public Integer[] getSelectedArtists() {
        return selectedArtists;
    }

    public void setSelectedArtists(Integer[] artistsList) {
        this.selectedArtists = artistsList;
    }

    public Integer[] getSelectedTrackId() {
        return selectedTrackId;
    }

    public void setSelectedTrackId(Integer[] selectedTrackId) {
        this.selectedTrackId = selectedTrackId;
    }
//    public List<Genres> getAlbumGenres() {
//        this.albums.forEach(item -> 
//            {item.getGenreToAlbumList().forEach(genreitem -> this.albumGenres.add(genreitem.getGenreId()));}
//        );
//        return this.albumGenres;
//    }
//    public List<String> getAllGenres() {
//        List<String> allGenresList = new ArrayList<>();
//        this.genresJpaController.findGenresEntities().forEach(item -> allGenresList.add(item.getGenreName()));
//        return allGenresList;
//    }

    public List<Genres> getAllGenres() {
        List<Genres> allGenresList = new ArrayList<>();
        this.genresJpaController.findGenresEntities().forEach(item -> allGenresList.add(item));
        return allGenresList;
    }

    public String getGenresListStr(Albums album1) {
        List<String> selectedList = new ArrayList<>();
        album1.getGenreToAlbumList().forEach(item -> selectedList.add(item.getGenreId().getGenreName()));
        String listStr = String.join(",", selectedList.toArray(new String[selectedList.size()]));
        return listStr;
    }

    public List<Artists> getAllArtists() {
        List<Artists> allArtistsList = new ArrayList<>();
        this.artistsJpaController.findArtistsEntities().forEach(item -> allArtistsList.add(item));
        return allArtistsList;
    }

    public String getArtistsListStr(Albums album) {
        List<String> selectedList = new ArrayList<>();
        album.getArtistAlbumsList().forEach(item -> selectedList.add(item.getArtistId().getArtistName()));
        String listStr = String.join(",", selectedList.toArray(new String[selectedList.size()]));
        return listStr;
    }

    public List<Tracks> getTrackListInAlbum(String albumIdStr) {
        int albumId = Integer.parseInt(albumIdStr);
        this.tracksListInAlbum = this.albumsJpaController.findAlbums(albumId).getTracksList();
        return this.tracksListInAlbum;
    }

    public List<Tracks> getAllTracks() {
        List<Tracks> allTracks = new ArrayList<>();
        this.tracksJpaController.findTracksEntities().forEach(item -> allTracks.add(item));
        return allTracks;
    }

    public void openNew() {
        this.selectedAlbum = new Albums();
        List<Integer> selectedList = new ArrayList<>();
        this.setSelectedGenres(selectedList.toArray(new Integer[selectedList.size()]));
        this.setSelectedArtists(selectedList.toArray(new Integer[selectedList.size()]));
        this.setIsRemoved(false);
    }

    public void openEdit(Albums selectedAlbum) {

        List<Integer> selectedGenresList = new ArrayList<>();
        selectedAlbum.getGenreToAlbumList().forEach(item -> selectedGenresList.add(item.getGenreId().getGenreId()));
        this.setSelectedGenres(selectedGenresList.toArray(new Integer[selectedGenresList.size()]));

        List<Integer> selectedArtistsList = new ArrayList<>();
        selectedAlbum.getArtistAlbumsList().forEach(item -> selectedArtistsList.add(item.getArtistId().getArtistId()));
        this.setSelectedArtists(selectedArtistsList.toArray(new Integer[selectedArtistsList.size()]));

        this.setIsRemoved(selectedAlbum.getRemovalStatus());

    }

    public void openAddNewTrack(Albums selectedAlbum) {
        this.selectedTrack = new Tracks();
        List<Integer> selectedList = new ArrayList<>();
        this.setSelectedGenres(selectedList.toArray(new Integer[selectedList.size()]));
        this.setSelectedArtists(selectedList.toArray(new Integer[selectedList.size()]));
    }


    public void saveAlbum() {

        try {
            if (this.selectedAlbum.getAlbumNumber() == null) {
                this.setRemovalStatus();
                albumsJpaController.create(this.selectedAlbum);
                this.saveGenreToAlbum();
                this.saveArtistsToAlbum();
                this.albums.add(this.selectedAlbum);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Album Added"));
            } else {
                this.setRemovalStatus();
                this.deleteGenreToAlbum();
                this.saveGenreToAlbum();
                this.deleteArtistsToAlbum();
                this.saveArtistsToAlbum();
                albumsJpaController.edit(this.selectedAlbum);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Album Updated"));

            }

        } catch (RollbackFailureException | IllegalOrphanException | NonexistentEntityException ex) {
            java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrimeFaces.current().executeScript("PF('manageAlbumDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-albums");
    }

    public void deleteAlbum() {
        this.deleteGenreToAlbum();
        this.deleteArtistsToAlbum();

        try {
            albumsJpaController.destroy(this.selectedAlbum.getAlbumNumber());
        } catch (IllegalOrphanException | NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
            java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.albums.remove(this.selectedAlbum);
        this.selectedAlbum = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Album Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-albums");
    }

    public void removeTrackFromAlbum() {
        try {
            this.selectedTrack.setRemoved(true);
            this.tracksJpaController.edit(this.selectedTrack);
        } catch (NonexistentEntityException ex) {
            java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Track Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-trackList");
    }

    public void addTrackToAlbum() {
        if (this.selectedTrack.getTrackId() == null) {
            try {
                this.setTrackFields();
                this.tracksJpaController.create(this.selectedTrack);
                this.saveGenreToTrack();
                this.saveArtistsToTrack();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Track Added"));
            } catch (RollbackFailureException ex) {
                java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrimeFaces.current().executeScript("PF('addTrackDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-trackList");
        }

    }

    private void setTrackFields() {
        this.selectedTrack.setSelectionNumber(this.selectedAlbum.getTotalTracks() + 1);
        this.selectedTrack.setPlayLength("1:00");
        this.selectedTrack.setEntryDate(new Date());
        this.selectedTrack.setAlbumNumber(this.selectedAlbum);

        for (Genres genre : this.getAllGenres()) {
            if (this.selectedGenres[0] == genre.getGenreId()) {
                this.selectedTrack.setMusicCategory(genre.getGenreName());
            }
        }
    }

    private void setRemovalStatus() {
        this.selectedAlbum.setRemovalStatus(this.isRemoved);
        //RemovalDate database format issue
        if (this.isRemoved) {
            this.selectedAlbum.setRemovalDate(true);
        } else {
            this.selectedAlbum.setRemovalDate(false);
        }
    }

    private void saveGenreToAlbum() {

        List<GenreToAlbum> genreAlbumsList = new ArrayList<>();
        for (Integer item : this.selectedGenres) {
            try {
                GenreToAlbum genreToAlbum = new GenreToAlbum();
                genreToAlbum.setAlbumNumber(this.selectedAlbum);
                genreToAlbum.setGenreId(this.genresJpaController.findGenres(item));
                this.genreToAlbumJpaController.create(genreToAlbum);
                genreAlbumsList.add(genreToAlbum);
            } catch (RollbackFailureException ex) {
                java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.selectedAlbum.setGenreToAlbumList(genreAlbumsList);
    }

    private void deleteGenreToAlbum() {

        if (this.selectedAlbum.getAlbumNumber() != null) {
            this.selectedAlbum.getGenreToAlbumList().forEach(genreToAlbum -> {
                try {
                    this.genreToAlbumJpaController.destroy(genreToAlbum.getTablekey());
                } catch (IllegalOrphanException | NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
                    java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private void saveArtistsToAlbum() {

        List<ArtistAlbums> artistAlbumsList = new ArrayList<>();
        for (Integer item : this.selectedArtists) {
            try {
                ArtistAlbums artistAlbums = new ArtistAlbums();
                artistAlbums.setAlbumNumber(this.selectedAlbum);
                artistAlbums.setArtistId(this.artistsJpaController.findArtists(item));
                this.artistAlbumsJpaController.create(artistAlbums);
                artistAlbumsList.add(artistAlbums);
            } catch (RollbackFailureException ex) {
                java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.selectedAlbum.setArtistAlbumsList(artistAlbumsList);
    }

    private void deleteArtistsToAlbum() {

        if (this.selectedAlbum.getAlbumNumber() != null) {
            this.selectedAlbum.getArtistAlbumsList().forEach(artistAlbum -> {
                try {
                    this.artistAlbumsJpaController.destroy(artistAlbum.getTablekey());
                } catch (NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
                    java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    private void saveGenreToTrack() {

        List<GenreToTracks> genreTrackList = new ArrayList<>();
        for (Integer item : this.selectedGenres) {
            try {
                GenreToTracks genreToTracks = new GenreToTracks();
                genreToTracks.setTrackId(this.selectedTrack);
                genreToTracks.setGenreId(this.genresJpaController.findGenres(item));
                this.genreToTracksJpaController.create(genreToTracks);
                genreTrackList.add(genreToTracks);
            } catch (RollbackFailureException ex) {
                java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.selectedTrack.setGenreToTracksList(genreTrackList);
    }

    private void saveArtistsToTrack() {

        List<ArtistsToTracks> artistTrackList = new ArrayList<>();
        for (Integer item : this.selectedArtists) {
            try {
                ArtistsToTracks artistTrack = new ArtistsToTracks();
                artistTrack.setTrackId(this.selectedTrack);
                artistTrack.setArtistId(this.artistsJpaController.findArtists(item));
                this.artistsToTracksJpaController.create(artistTrack);
                artistTrackList.add(artistTrack);
            } catch (RollbackFailureException ex) {
                java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.selectedTrack.setArtistsToTracksCollection(artistTrackList);
    }

}
