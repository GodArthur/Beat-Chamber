package com.beatchamber.beans;

import com.beatchamber.entities.Albums;
import com.beatchamber.entities.Tracks;
import com.beatchamber.jpacontroller.AlbumsJpaController;
import com.beatchamber.jpacontroller.TracksJpaController;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.LoggerFactory;

/**
 * Class contains all of the queries for searching for musical content (Tracks
 * and albums)
 *
 * @author Korjon Chang-Jones
 */
@Named("search")
@SessionScoped
public class SearchEngine implements Serializable {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(SearchEngine.class);

    //The dropdown selected item
    private String searchCondition;

    //The query String
    private String searchValue;
    
    //Data structure containing tracks and albums
    private List<MusicComponent> musicComponents;
    
    private String firstDate;
    
    private String lastDate;

    @Inject
    AlbumsJpaController albumController;
    
    @Inject
    TracksJpaController trackController;
    
    
    public SearchEngine() {

        //default search Condition
        searchCondition = "albumTitle";
        
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
    

    /**
     * Method checks if the selected search condition is based on the date added
     *
     * @return
     */
    public boolean getDateCondition() {

        return searchCondition.equals("dateAdded");
    }
    
    /**
     * Method finds Music content (albums and/or tracks)
     * based on a search condition
     */
    public void findMusicContent(){
        
        musicComponents = new ArrayList<>();

        
        switch(searchCondition){
            
            
            case "trackName":
                
                findTracks();
                break;
            
            case "dateAdded":
                
                findMusicContentByDate();
                break;
                
            case "artistName":
                
                findMusicContentByArtist();
                break;
                
                
            default:
                
                findAlbums();
        }
        
        
    }
    
    /**
     * Method finds music content (albums and tracks)
     * entered in the inventory between certain dates
     */
    public void findMusicContentByDate(){
        
        try {     
            var startDate = new SimpleDateFormat("yyyy/MM/dd").parse(firstDate);
            var endDate = new SimpleDateFormat("yyyy/MM/dd").parse(lastDate);

            List<Tracks> tracks = trackController.findTracksByDate(startDate, endDate);
            List<Albums> albums = albumController.findAlbumsByDate(startDate, endDate);
            
            storeTracks(tracks);
            storeAlbums(albums);

        } catch (ParseException ex) {
            Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
    }
    
    private void findMusicContentByArtist(){
        
       
    }
    
    /**
     * Method finds tracks by track name
     */
    private void findTracks(){
        
        List<Tracks> tracks = trackController.findTracksByTitle(searchValue);
        storeTracks(tracks);
    }
    
    /**
     * Method stores tracks into a music component
     * @param tracks 
     */
    private void storeTracks(List<Tracks> tracks){
                
        for(var track : tracks){
            
            TrackBackingBean trackBackingBean = new TrackBackingBean(track);
            musicComponents.add(trackBackingBean);
            
        }
    }
    
    /**
     * Method finds albums by album Name
     */
    private void findAlbums(){
        
        List<Albums> albums = albumController.findAlbumsByTitle(searchValue);
        
        storeAlbums(albums);
        
    }
    
    
    /**
     * Method stores albums into a music component
     * @param albums 
     */
    private void storeAlbums(List<Albums> albums){
        
        
        //Looping through every album
        //Creating a bean with the album
        //Storing the bean in the interface
        albums.stream().map(album -> new AlbumBackingBean(album)).forEachOrdered(albumBackingBean -> {
            musicComponents.add(albumBackingBean);
        });
    }

}
