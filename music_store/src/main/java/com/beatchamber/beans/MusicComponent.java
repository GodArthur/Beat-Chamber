
package com.beatchamber.beans;

import com.beatchamber.entities.Artists;
import java.util.List;


/**
 *
 * @author Korjon Chang-Jones
 */
public interface MusicComponent {
    
    
    String getTitle();
    
    List<Artists> getArtists();
    
    String getCoverPath();
    
}
