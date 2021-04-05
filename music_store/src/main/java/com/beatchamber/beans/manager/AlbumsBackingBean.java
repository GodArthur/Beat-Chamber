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
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.ArtistAlbumsJpaController;
import com.beatchamber.jpacontroller.ArtistsJpaController;
import com.beatchamber.jpacontroller.GenreToAlbumJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import com.beatchamber.jpacontroller.GenresJpaController;
import java.io.Serializable;
import java.util.ArrayList;
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

    private List<Albums> albums;
    private Albums selectedAlbum;
    private Tracks selectedTrack;
    private List<Tracks> tracksListInAlbum;
    private Tracks newTrack;

    private boolean isAddTrack;
    private boolean isRemoved;

    private Integer[] selectedGenres;
    private Integer[] selectedArtists;

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

    public Tracks getNewTrack() {
        return newTrack;
    }

    public void setNewTrack(Tracks newTrack) {
        this.newTrack = newTrack;
    }

    public boolean getIsAddTrack() {
        return isAddTrack;
    }

    public void setIsAddTrack(boolean isAddTrack) {
        this.isAddTrack = isAddTrack;
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

    public Integer[] getSelectedGenres() {
        return selectedGenres;
    }

    public void setSelectedGenres(Integer[] genreList) {
        this.selectedGenres = genreList;
    }

    public String getGenresListStr(int albumNumber) {
        Albums album1 = this.selectedAlbum;
        String listStr = "";
        List<String> selectedList = new ArrayList<>();
//        if (this.selectedAlbum.getAlbumNumber() != null) {
//            this.selectedAlbum.getGenreToAlbumList().forEach(item -> selectedList.add(item.getGenreId().getGenreName()));
//            listStr = String.join(",", selectedList.toArray(new String[selectedList.size()]));
//        }
        for (Albums album : this.albums) {
            if (album.getAlbumNumber() == albumNumber) {
                album.getGenreToAlbumList().forEach(item -> selectedList.add(item.getGenreId().getGenreName()));
                listStr = String.join(", ", selectedList.toArray(new String[selectedList.size()]));
            }
        }
        return listStr;
    }

    public List<Artists> getAllArtists() {
        List<Artists> allArtistsList = new ArrayList<>();
        this.artistsJpaController.findArtistsEntities().forEach(item -> allArtistsList.add(item));
        return allArtistsList;
    }

    public Integer[] getSelectedArtists() {
        return selectedArtists;
    }

    public void setSelectedArtists(Integer[] artistsList) {
        this.selectedArtists = artistsList;
    }

    public String getArtistsListStr(int albumNumber) {
        String listStr = "";
        List<String> selectedList = new ArrayList<>();
//        if (this.selectedAlbum.getAlbumNumber() != null) {
//            this.selectedAlbum.getArtistAlbumsList().forEach(item -> selectedList.add(item.getArtistId().getArtistName()));
//            listStr = String.join(",", selectedList.toArray(new String[selectedList.size()]));
//        }
        for (Albums album : this.albums) {
            if (album.getAlbumNumber() == albumNumber) {
                album.getArtistAlbumsList().forEach(item -> selectedList.add(item.getArtistId().getArtistName()));
                listStr = String.join(", ", selectedList.toArray(new String[selectedList.size()]));
            }
        }
        return listStr;
    }

    public boolean getIsRemoved() {
        return this.isRemoved;
    }

    public void setIsRemoved(boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public void openNew() {
        this.selectedAlbum = new Albums();
        List<Integer> selectedList = new ArrayList<>();
        this.setSelectedGenres(selectedList.toArray(new Integer[selectedList.size()]));
        this.setSelectedArtists(selectedList.toArray(new Integer[selectedList.size()]));
        this.setIsRemoved(false);
    }

    public void openEdit(Albums selectedAlbum) {

        if (selectedAlbum.getAlbumNumber() != null) {
            List<Integer> selectedGenresList = new ArrayList<>();
            selectedAlbum.getGenreToAlbumList().forEach(item -> selectedGenresList.add(item.getGenreId().getGenreId()));
            this.setSelectedGenres(selectedGenresList.toArray(new Integer[selectedGenresList.size()]));

            List<Integer> selectedArtistsList = new ArrayList<>();
            selectedAlbum.getArtistAlbumsList().forEach(item -> selectedArtistsList.add(item.getArtistId().getArtistId()));
            this.setSelectedArtists(selectedArtistsList.toArray(new Integer[selectedArtistsList.size()]));
        }

        this.setIsRemoved(selectedAlbum.getRemovalStatus());

    }

    public List<Tracks> getTrackListInAlbum(String albumIdStr) {
        int albumId = Integer.parseInt(albumIdStr);
        this.tracksListInAlbum = this.albumsJpaController.findAlbums(albumId).getTracksList();
        return this.tracksListInAlbum;
    }

    public List<String> getAllTracks() {
        List<String> allTracks = new ArrayList<>();
        this.tracksJpaController.findTracksEntities().forEach(item -> allTracks.add(item.getTrackTitle()));
        return allTracks;
    }

    public void saveAlbum() {

        try {
            if (this.selectedAlbum.getAlbumNumber() == null) {
                this.setRemovalStatus();
                albumsJpaController.create(this.selectedAlbum);
                this.saveGenreToAlbum();
                this.saveArtistsToAlbum();
                albumsJpaController.edit(this.selectedAlbum);
                this.albums.add(this.selectedAlbum);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Album Added"));
            } else {
                this.setRemovalStatus();
                this.saveGenreToAlbum();
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

    }

    public void addTrackToAlbum() {
        this.isAddTrack = false;
    }

    public void onItemChange() {

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
        this.deleteGenreToAlbum();
        List<GenreToAlbum> genreAlbumsList = new ArrayList<>();
        for (Integer item : this.selectedGenres) {
            try {
                GenreToAlbum genreToAlbum = new GenreToAlbum();
                genreToAlbum.setAlbumNumber(this.selectedAlbum);
                genreToAlbum.setGenreId(this.genresJpaController.findGenres(item));
                this.genreToAlbumJpaController.create(genreToAlbum);

                genreAlbumsList.add(genreToAlbum);
                this.selectedAlbum.setGenreToAlbumList(genreAlbumsList);
            } catch (RollbackFailureException ex) {
                java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void deleteGenreToAlbum() {

        if (this.selectedAlbum.getAlbumNumber() != null) {
            List<GenreToAlbum> genreToAlbumList = this.genreToAlbumJpaController.findGenreToAlbumEntities();
            for (GenreToAlbum genreToAlbum : genreToAlbumList) {
                if (this.selectedAlbum.getAlbumNumber() == genreToAlbum.getAlbumNumber().getAlbumNumber()) {
                    try {
                        this.genreToAlbumJpaController.destroy(genreToAlbum.getTablekey());
                    } catch (RollbackFailureException | IllegalOrphanException | NonexistentEntityException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
                        java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    private void saveArtistsToAlbum() {
        this.deleteArtistsToAlbum();
        List<ArtistAlbums> artistAlbumsList = new ArrayList<>();
        for (Integer item : this.selectedArtists) {
            try {
                ArtistAlbums artistAlbums = new ArtistAlbums();
                artistAlbums.setAlbumNumber(this.selectedAlbum);
                artistAlbums.setArtistId(this.artistsJpaController.findArtists(item));
                this.artistAlbumsJpaController.create(artistAlbums);

                artistAlbumsList.add(artistAlbums);
                this.selectedAlbum.setArtistAlbumsList(artistAlbumsList);
            } catch (RollbackFailureException ex) {
                java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void deleteArtistsToAlbum() {

        if (this.selectedAlbum.getAlbumNumber() != null) {
            List<ArtistAlbums> artistToAlbumList = this.artistAlbumsJpaController.findArtistAlbumsEntities();
            for (ArtistAlbums artistAlbum : artistToAlbumList) {
                if (this.selectedAlbum.getAlbumNumber() == artistAlbum.getAlbumNumber().getAlbumNumber()) {
                    try {
                        this.artistAlbumsJpaController.destroy(artistAlbum.getTablekey());
                    } catch (NonexistentEntityException | NotSupportedException | SystemException | RollbackFailureException | RollbackException | HeuristicMixedException | HeuristicRollbackException ex) {
                        java.util.logging.Logger.getLogger(AlbumsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

}
