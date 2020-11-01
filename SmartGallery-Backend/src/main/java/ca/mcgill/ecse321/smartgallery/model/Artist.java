package ca.mcgill.ecse321.smartgallery.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Artist extends Profile{
private Set<Artwork> artworks;

@ManyToMany
public Set<Artwork> getArtworks() {
   return this.artworks;
}

public void setArtworks(Set<Artwork> artworkss) {
   this.artworks = artworkss;
}

private boolean isVerified;

public void setIsVerified(boolean value) {
this.isVerified = value;
    }
public boolean isVerified() {
return this.isVerified;
       }
   }
