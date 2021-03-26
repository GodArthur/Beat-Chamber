package com.beatchamber.beans;

import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
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
    
    public String sendAlbum(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        this.albumId = Integer.parseInt(params.get("albumId"));
        return "album_page.xhtml";
    }

}

