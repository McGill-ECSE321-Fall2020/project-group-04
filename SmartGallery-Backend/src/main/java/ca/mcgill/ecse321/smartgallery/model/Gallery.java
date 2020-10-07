package ca.mcgill.ecse321.smartgallery.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@Entity
public class Gallery{
private double comissionPercentage;
   
   public void setComissionPercentage(double value) {
this.comissionPercentage = value;
    }
public double getComissionPercentage() {
return this.comissionPercentage;
    }
private Set<Artwork> artwork;

@OneToMany(mappedBy="gallery", cascade={CascadeType.ALL})
public Set<Artwork> getArtwork() {
   return this.artwork;
}

public void setArtwork(Set<Artwork> artworks) {
   this.artwork = artworks;
}

private SmartGallery smartGallery;

@OneToOne(optional=false)
public SmartGallery getSmartGallery() {
   return this.smartGallery;
}

public void setSmartGallery(SmartGallery smartGallery) {
   this.smartGallery = smartGallery;
}

private Set<Listing> listing;

@OneToMany(mappedBy="gallery", cascade={CascadeType.ALL})
public Set<Listing> getListing() {
   return this.listing;
}

public void setListing(Set<Listing> listings) {
   this.listing = listings;
}

private String galleryName;

public void setGalleryName(String value) {
this.galleryName = value;
    }
@Id
public String getGalleryName() {
return this.galleryName;
       }
   }
