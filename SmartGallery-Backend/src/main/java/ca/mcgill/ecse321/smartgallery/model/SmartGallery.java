package ca.mcgill.ecse321.smartgallery.model;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class SmartGallery{
private Set<Profile> profile;

@OneToMany(mappedBy="smartGallery", cascade={CascadeType.ALL})
public Set<Profile> getProfile() {
   return this.profile;
}

public void setProfile(Set<Profile> profiles) {
   this.profile = profiles;
}

private Set<Transaction> transaction;

@OneToMany(mappedBy="smartGallery", cascade={CascadeType.ALL})
public Set<Transaction> getTransaction() {
   return this.transaction;
}

public void setTransaction(Set<Transaction> transactions) {
   this.transaction = transactions;
}

private Gallery gallery;

@OneToOne(mappedBy="smartGallery", cascade={CascadeType.ALL})
public Gallery getGallery() {
   return this.gallery;
}

public void setGallery(Gallery gallery) {
   this.gallery = gallery;
}
private int smartGalleryID;

public void setSmartGalleryID(int value) {
this.smartGalleryID = value;
    }
@Id
public int getSmartGalleryID() {
return this.smartGalleryID;
       }
}

