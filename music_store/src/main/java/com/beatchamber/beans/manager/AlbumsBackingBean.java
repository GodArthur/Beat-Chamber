/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beatchamber.beans.manager;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Tracks;
import com.beatchamber.exceptions.IllegalOrphanException;
import com.beatchamber.exceptions.NonexistentEntityException;
import com.beatchamber.exceptions.RollbackFailureException;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import com.beatchamber.jpacontroller.ProvincesJpaController;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.primefaces.PrimeFaces;
import org.primefaces.util.LangUtils;
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

    private List<Albums> albums;

    private Albums selectedAlbum;

    private List<Tracks> tracksListInAlbum;

    private Tracks newTrack;

    @PostConstruct
    public void init() {
        this.albums = albumsJpaController.findAlbumsEntities();
    }

    public List<Albums> getAlbums() {
        return albums;
    }

    public Albums getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setSelectedAlbum(Albums selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }

    public Tracks getNewTrack() {
        return newTrack;
    }

    public void setNewTrack(Tracks newTrack) {
        this.newTrack = newTrack;
    }

    public void openNew() {
        this.selectedAlbum = new Albums();
    }

    public List<Tracks> getTrackListInAlbum(String albumIdStr) {
        int albumId = Integer.parseInt(albumIdStr);
        this.tracksListInAlbum = this.albumsJpaController.findAlbums(albumId).getTracksList();
        return this.tracksListInAlbum;
    }

    public List<String> getAllTracks() {
        List<String> allTracksList = new ArrayList<>();
        this.tracksJpaController.findTracksEntities().forEach(item -> allTracksList.add(item.getTrackTitle()));
        return allTracksList;
    }

    public void saveAlbum() {
        try {
            if (this.selectedAlbum.getAlbumNumber() == null) {
                albumsJpaController.create(this.selectedAlbum);
                this.albums.add(this.selectedAlbum);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Album Added"));
            } else {
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

    public void addNewTrack() {

    }

    public void addTrackToAlbum() {

    }
}
