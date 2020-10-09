package ca.mcgill.ecse321.smartgallery.dao;

import java.sql.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.smartgallery.model.*;

public class SmartGalleryCentralRepository {
	
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
}
