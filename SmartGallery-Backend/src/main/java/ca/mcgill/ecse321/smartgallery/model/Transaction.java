package ca.mcgill.ecse321.smartgallery.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;
import java.sql.Date;
import javax.persistence.OneToOne;

@Entity
public class Transaction{
private int transactionID;
   
   public void setTransactionID(int value) {
this.transactionID = value;
    }
@Id
public int getTransactionID() {
return this.transactionID;
    }
private PaymentMethod paymentMethod;

public void setPaymentMethod(PaymentMethod value) {
this.paymentMethod = value;
    }
public PaymentMethod getPaymentMethod() {
return this.paymentMethod;
    }
private DeliveryMethod deliveryMethod;

public void setDeliveryMethod(DeliveryMethod value) {
this.deliveryMethod = value;
    }
public DeliveryMethod getDeliveryMethod() {
return this.deliveryMethod;
    }
private SmartGallery smartGallery;

@ManyToOne(optional=false)
public SmartGallery getSmartGallery() {
   return this.smartGallery;
}

public void setSmartGallery(SmartGallery smartGallery) {
   this.smartGallery = smartGallery;
}
private Customer customer;

@ManyToOne(optional=false)
public Customer getCustomer() {
   return this.customer;
}

public void setCustomer(Customer customer) {
   this.customer = customer;
}

private Date paymentDate;

public void setPaymentDate(Date value) {
this.paymentDate = value;
    }
public Date getPaymentDate() {
return this.paymentDate;
    }
private Listing listing;

@OneToOne(optional=false)
public Listing getListing() {
   return this.listing;
}

public void setListing(Listing listing) {
   this.listing = listing;
}

}
