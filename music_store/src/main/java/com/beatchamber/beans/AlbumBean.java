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

    /**
     * Default constructor
     */
    public AlbumBean() {
        this.albumId = 0;
    }
    
    /**
     * @return The album id
     */
    public int getAlbumId(){
        return this.albumId;
    }
    
    /**
     * Set the album id
     * @param albumId 
     */
    public void setAlbumId(Integer albumId){
        this.albumId = albumId;
    }
    
    /**
     * Stores the sent param in albumId, and navigates to the album page
     * @return The album page
     */
    public String sendAlbum(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        this.albumId = Integer.parseInt(params.get("albumId"));
        
        return "album_page.xhtml?albumId=" + this.albumId;
    }

}

