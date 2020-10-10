package ca.mcgill.ecse321.smartgallery.model;

import javax.persistence.Entity;
import java.sql.Date;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Listing{
private Date listedDate;
   
   public void setListedDate(Date value) {
this.listedDate = value;
    }
public Date getListedDate() {
return this.listedDate;
    }
private boolean isSold;

public void setIsSold(boolean value) {
this.isSold = value;
    }
public boolean isIsSold() {
return this.isSold;
    }
private Artwork artwork;

@OneToOne
public Artwork getArtwork() {
   return this.artwork;
}

public void setArtwork(Artwork artwork) {
   this.artwork = artwork;
}

private Transaction transaction;

@OneToOne(mappedBy="listing")
public Transaction getTransaction() {
   return this.transaction;
}

public void setTransaction(Transaction transaction) {
   this.transaction = transaction;
}

private Gallery gallery;

@ManyToOne(optional=false)
public Gallery getGallery() {
   return this.gallery;
}

public void setGallery(Gallery gallery) {
   this.gallery = gallery;
}

private int listingID;

public void setListingID(int value) {
this.listingID = value;
    }
@Id
public int getListingID() {
return this.listingID;
       }
   }
