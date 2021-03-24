package com.beatchamber.beans;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
    
    public int getAlbumId(){
        
        return this.albumId;
    }
    
    public void setAlbumId(Integer albumId){
        this.albumId = albumId;
    }
    
    public String sendAlbum(Integer albumId){
        this.albumId = albumId;
        return "album_page.xhtml";
    }
}
