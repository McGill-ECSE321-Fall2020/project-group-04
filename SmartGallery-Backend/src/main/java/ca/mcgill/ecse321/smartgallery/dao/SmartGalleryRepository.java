package ca.mcgill.ecse321.smartgallery.dao;

import java.sql.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.smartgallery.model.*;

public class SmartGalleryRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Artist createArtist(String username, Date date, SmartGallery smartGallery,
			String email, String password) {
		Artist a = new Artist();
		a.setUsername(username);
		a.setCreationDate(date);
		a.setDefaultPaymentMethod(null);
		a.setEmail(email);
		a.setIsVerified(false);
		a.setPassword(password);
		a.setSmartGallery(smartGallery);
		entityManager.persist(a);
		return a;
	}
	
	@Transactional
	public Artist getArtist(String username) {
		Artist a = entityManager.find(Artist.class, username);
		return a;
	}
	
	@Transactional
	public Artwork createArtwork(int artworkID, Set<Artist> artists, Gallery gallery, int height, String name, 
			double price, ArtStyle style, int weight, int width, int year) {
		Artwork a = new Artwork();
		a.setArtworkID(artworkID);
		a.setArtists(artists);
		a.setGallery(gallery);
		a.setHeight(height);
		a.setIsBeingPromoted(false);
		a.setName(name);
		a.setPrice(price);
		a.setStyle(style);
		a.setWeight(weight);
		a.setWidth(width);
		a.setYear(year);
		entityManager.persist(a);
		return a;
	}
	
	@Transactional
	public Artwork getArtwork(int artworkID) {
		Artwork a = entityManager.find(Artwork.class, artworkID);
		return a;
	}
	
	@Transactional
	public Customer createCustomer(String username, Date date, String email, String password, 
			SmartGallery smartGallery) {
		Customer c = new Customer();
		c.setUsername(username);
		c.setCreationDate(date);
		c.setDefaultPaymentMethod(null);
		c.setEmail(email);
		c.setPassword(password);
		c.setSmartGallery(smartGallery);
		entityManager.persist(c);
		return c;
	}
	
	@Transactional 
	public Customer getCustomer(String username)
	{
		Customer customer = entityManager.find(Customer.class, username);
		return customer;
	}
	
	@Transactional
	public Listing createListing(int id, boolean isSold, Artwork artwork, Date dateListed, Transaction transaction,
			Gallery gallery) {
		
		Listing listing = new Listing();
		listing.setGallery(gallery);
		listing.setListingID(id);
		listing.setIsSold(isSold);
		listing.setArtwork(artwork);
		listing.setListedDate(dateListed);
		listing.setTransaction(transaction);
		listing.setGallery(gallery);
		entityManager.persist(listing);
		
		return listing;
		
	}
	
	@Transactional
	public Listing getListing(int listingID)
	{
		Listing listing = entityManager.find(Listing.class, listingID);
		return listing;
				
	}
	
	@Transactional 
	public Transaction createTransaction(int transactionID, PaymentMethod paymentMethod, DeliveryMethod deliveryMethod, SmartGallery smartGallery, 
			Set<Profile> profiles, Date paymentDate, Listing listing)
	{
		Transaction transaction = new Transaction();
		transaction.setTransactionID(transactionID);
		transaction.setPaymentMethod(paymentMethod);
		transaction.setDeliveryMethod(deliveryMethod);
		transaction.setProfile(profiles);
		transaction.setPaymentDate(paymentDate);
		transaction.setListing(listing);
		entityManager.persist(transaction);
		
		return transaction;
	}
	
	@Transactional
	public Transaction getTransaction(int transactionID)
	{
		Transaction transaction = entityManager.find(Transaction.class, transactionID);
		
		return transaction;
	}
	
	@Transactional 
	public Gallery createGallery(String galleryName, Set<Artwork> artworks, SmartGallery smartGallery, 
			Set<Listing> listings)
	{
		Gallery gallery = new Gallery();
		gallery.setGalleryName(galleryName);
		gallery.setArtwork(artworks);
		gallery.setSmartGallery(smartGallery);
		gallery.setListing(listings);
		entityManager.persist(gallery);
		return gallery;
	}
	
	@Transactional
	public Gallery getGallery(String galleryName)
	{
		Gallery gallery = entityManager.find(Gallery.class, galleryName);
		
		return gallery;
	}
}
