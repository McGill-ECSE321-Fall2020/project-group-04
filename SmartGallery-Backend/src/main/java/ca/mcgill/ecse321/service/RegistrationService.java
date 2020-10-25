package ca.mcgill.ecse321.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.*;
import ca.mcgill.ecse321.smartgallery.model.*;

public class RegistrationService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ArtistRepository artistRepository;
	
	/**
	 * @author roeywine
	 * Method that creates and saves a customer
	 * 
	 * @param username 				Username for the profile
	 * @param password 				Password for the profile
	 * @param email 				Email to be used in the profile
	 * @param defaultPaymentMethod 	A default payment method for purchases
	 * @param creationDate 			Date the customer profile was created
	 * @param smartGallery 			The smartGallery system
	 * @param transactions			The transactions the customer has/had
	 * @param artworksViewed		Artwork pieces viewed by the customer
	 * @return customer				The created customer
	 */
	@Transactional
	public Customer createCustomer(String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate, SmartGallery smartGallery, Set<Transaction> transactions, Set<Artwork> artworksViewed) {
		
		// Create a customer object and initialize all parameters
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.setDefaultPaymentMethod(defaultPaymentMethod);
		customer.setCreationDate(creationDate);
		customer.setSmartGallery(smartGallery);
		customer.setTransaction(transactions);
		customer.setArtworksViewed(artworksViewed);
		customerRepository.save(customer);		// Save to customer repository
		return customer;						// Return customer with updated parameters
	}
	
	/**
	 * @author roeywine
	 * Method that gets an existing customer given a username
	 * 
	 * @param username 				Username for the profile
	 */
	@Transactional
	public Customer getCustomer(String username) {
		
		// Uses existing method in customer repository to find a customer by username
		Customer customer = customerRepository.findCustomerByUsername(username);
		return customer;
	}
	
	/**
	 * @author roeywine
	 * Method that gets all existing customers
	 */
	@Transactional
	public List<Customer> getAllCustomers() {
		
		// Uses existing method in customer repository to find all the existing customers
		return toList(customerRepository.findAll());
	}
	
	/**
	 * @author roeywine
	 * Method that creates and saves an artist
	 * 
	 * @param username 				Username for the profile
	 * @param password 				Password for the profile
	 * @param email 				Email to be used in the profile
	 * @param defaultPaymentMethod 	A default payment method for purchases
	 * @param creationDate 			Date the customer profile was created
	 * @param isVerified			Whether or not the author is verified by the system
	 * @param smartGallery 			The smartGallery system
	 * @param transactions			The transactions the customer has/had
	 * @param artworks				Artwork pieces created by the artist
	 */
	@Transactional
	public Artist createArtist(String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate, boolean isVerified, SmartGallery smartGallery, Set<Transaction> transactions, Set<Artwork> artworks) {
		
		// Create a customer object and initialize all parameters
		Artist artist = new Artist();
		artist.setUsername(username);
		artist.setPassword(password);
		artist.setEmail(email);
		artist.setDefaultPaymentMethod(defaultPaymentMethod);
		artist.setCreationDate(creationDate);
		artist.setIsVerified(false); 			// When an artists profile is made, they are not verified initially
		artist.setSmartGallery(smartGallery);
		artist.setTransaction(transactions);
		artist.setArtworks(artworks);
		artistRepository.save(artist);			// Save to customer repository
		return artist;							// Return artist with updated parameters
	}
	
	/**
	 * @author roeywine
	 * Method that gets an existing artist given a username
	 * 
	 * @param username 				Username for the profile
	 */
	@Transactional
	public Artist getArtist(String username) {
		
		// Uses existing method in artist repository to find an artist by username
		Artist artist = artistRepository.findArtistByUsername(username);
		return artist;
	}
	
	/**
	 * @author roeywine
	 * Method that gets all existing artists
	 */
	@Transactional
	public List<Artist> getAllArtists() {
		
		// Uses existing method in customer repository to find all the existing customers
		return toList(artistRepository.findAll());
	}
	
	// Helper method to retrieve lists of objects
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
