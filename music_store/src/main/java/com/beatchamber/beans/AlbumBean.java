package com.beatchamber.beans;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;



/**
 * Managed bean for the Albums, passing to the album page
 * 
 * @author Susan Vuu - 1735488
 */
@Named("album")
@RequestScoped
public class AlbumBean implements Serializable {
    
    private String albumId;
    
    public String getAlbumId(){
        return this.albumId;
    }
    
    public void setAlbumId(String albumId){
        this.albumId = albumId;
    }
    
    public String sendAlbum(String albumId){
        this.albumId = albumId;
        return "album_page.xhtml";
    }
    
    public String sendAlbum(){
        return "album_page.xhtml";
    }
}
