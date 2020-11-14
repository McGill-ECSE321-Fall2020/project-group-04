package ca.mcgill.ecse321.smartgallery.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Customer extends Profile{
private Set<Artwork> artworksViewed;

@ManyToMany
public Set<Artwork> getArtworksViewed() {
   return this.artworksViewed;
}

public void setArtworksViewed(Set<Artwork> artworksVieweds) {
   this.artworksViewed = artworksVieweds;
}
}
