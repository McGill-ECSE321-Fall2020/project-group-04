package ca.mcgill.ecse321.smartgallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.sql.Date;
import java.util.Set;

@Entity
public abstract class Profile{
private String username;
   
   public void setUsername(String value) {
this.username = value;
    }
@Id
public String getUsername() {
return this.username;
    }
private String password;

public void setPassword(String value) {
this.password = value;
    }
public String getPassword() {
return this.password;
    }
private String email;

public void setEmail(String value) {
this.email = value;
    }
public String getEmail() {
return this.email;
    }
private PaymentMethod defaultPaymentMethod;

public void setDefaultPaymentMethod(PaymentMethod value) {
this.defaultPaymentMethod = value;
    }
public PaymentMethod getDefaultPaymentMethod() {
return this.defaultPaymentMethod;
    }
private SmartGallery smartGallery;

@ManyToOne(optional=false)
public SmartGallery getSmartGallery() {
   return this.smartGallery;
}

public void setSmartGallery(SmartGallery smartGallery) {
   this.smartGallery = smartGallery;
}

private Date creationDate;

public void setCreationDate(Date value) {
this.creationDate = value;
    }
public Date getCreationDate() {
return this.creationDate;
       }

private boolean loggedIn;

public void setLoggedIn(boolean loggedIn) {
	this.loggedIn = loggedIn;
}

public void login() {
	loggedIn = true;
}

public void logout() {
	loggedIn = false;
}

public boolean isLoggedIn() {
	return loggedIn;
}
private Set<Transaction> transaction;

@OneToMany(mappedBy="profile")
public Set<Transaction> getTransaction() {
   return this.transaction;
}

public void setTransaction(Set<Transaction> transactions) {
   this.transaction = transactions;
}
private Set<Artwork> artworksViewed;

@ManyToMany
public Set<Artwork> getArtworksViewed() {
   return this.artworksViewed;
}

public void setArtworksViewed(Set<Artwork> artworksVieweds) {
   this.artworksViewed = artworksVieweds;
}
}
