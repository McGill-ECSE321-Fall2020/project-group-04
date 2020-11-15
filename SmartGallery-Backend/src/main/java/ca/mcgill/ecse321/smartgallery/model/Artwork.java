package ca.mcgill.ecse321.smartgallery.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@Entity
public class Artwork{
private String name;
   
   public void setName(String value) {
this.name = value;
    }
public String getName() {
return this.name;
    }
private int year;

public void setYear(int value) {
this.year = value;
    }
public int getYear() {
return this.year;
    }
private double price;

public void setPrice(double value) {
this.price = value;
    }
public double getPrice() {
return this.price;
    }
private boolean isBeingPromoted;

public void setIsBeingPromoted(boolean value) {
this.isBeingPromoted = value;
    }
public boolean isIsBeingPromoted() {
return this.isBeingPromoted;
    }
private ArtStyle style;

public void setStyle(ArtStyle value) {
this.style = value;
    }
public ArtStyle getStyle() {
return this.style;
    }
private int height;

public void setHeight(int value) {
this.height = value;
    }
public int getHeight() {
return this.height;
    }
private int weight;

public void setWeight(int value) {
this.weight = value;
    }
public int getWeight() {
return this.weight;
    }
private int width;

public void setWidth(int value) {
this.width = value;
    }
public int getWidth() {
return this.width;
    }
private Set<Artist> artists;

private String imageUrl;
public String getImageUrl() {
  return this.imageUrl;
}
public void setImageUrl(String imageUrl) {
  this.imageUrl = imageUrl;
}

@ManyToMany(mappedBy="artworks")
public Set<Artist> getArtists() {
   return this.artists;
}

public void setArtists(Set<Artist> artistss) {
   this.artists = artistss;
}

private Set<Customer> interestedCustomer;

@ManyToMany(mappedBy="artworksViewed")
public Set<Customer> getInterestedCustomer() {
   return this.interestedCustomer;
}

public void setInterestedCustomer(Set<Customer> interestedCustomers) {
   this.interestedCustomer = interestedCustomers;
}

private Gallery gallery;

@ManyToOne(optional=false)
public Gallery getGallery() {
   return this.gallery;
}

public void setGallery(Gallery gallery) {
   this.gallery = gallery;
}

private Listing listing;

@OneToOne(mappedBy="artwork", cascade=CascadeType.ALL)
public Listing getListing() {
   return this.listing;
}

public void setListing(Listing listing) {
   this.listing = listing;
}

private int artworkID;

public void setArtworkID(int value) {
this.artworkID = value;
    }
@Id
public int getArtworkID() {
return this.artworkID;
       }
   }
