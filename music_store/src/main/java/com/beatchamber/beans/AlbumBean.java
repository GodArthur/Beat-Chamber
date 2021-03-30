package com.beatchamber.beans;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Genres;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;



/**
 * Managed bean for the Albums, passing to the album page
 * 
 * @author Susan Vuu - 1735488
 */
@Named("album")
@SessionScoped
public class AlbumBean implements Serializable {
    
    private int albumId;
     
    private List<Albums> similarAlbums;
    
    @Inject
    private AlbumsJpaController albumController;

    //Default constructor required
    public AlbumBean() {
        this.albumId = 0;
    }
    
    public int getAlbumId(){
        
        return this.albumId;
    }
    
    public void setAlbumId(Integer albumId){
        this.albumId = albumId;
    }
    
    public List<Albums> getSimilarAlbums(){
        
        return similarAlbums.subList(0, 3);
    }
    
    public String sendAlbum(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        this.albumId = Integer.parseInt(params.get("albumId"));
        
        storeSimilarAlbums(albumController.findGenre(albumId));
        return "album_page.xhtml";
    }
    
    /**
     * Method gets albums that are similar to the contents
     * (album or track) being viewed.
     * Filtered by genre
     * @param genre
     * @author Korjon Chang-Jones
     */
    public void storeSimilarAlbums(Genres genre){
        
        String title = albumController.findAlbums(albumId).getAlbumTitle();
        similarAlbums = albumController.findAlbumsByGenre(genre, title);
        
        //Used to randomize the album order so that they
        //don't always appear in the same order when displayed
        Collections.shuffle(similarAlbums);
    }
    

}

