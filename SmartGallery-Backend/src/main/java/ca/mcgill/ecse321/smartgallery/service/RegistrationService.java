package ca.mcgill.ecse321.smartgallery.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.controller.Converters;
import ca.mcgill.ecse321.smartgallery.dao.*;
import ca.mcgill.ecse321.smartgallery.model.*;

@Service
public class RegistrationService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ArtistRepository artistRepository;

	/* **** CUSTOMER METHODS **** */

	/**
	 * @author roeywine Method that creates and saves a customer
	 * 
	 * @param username             Username for the profile
	 * @param password             Password for the profile
	 * @param email                Email to be used in the profile
	 * @param defaultPaymentMethod A default payment method for purchases
	 * @return customer The created customer
	 */
	@Transactional
	public Customer createCustomer(String username, String password, String email, String defaultPaymentMethod) {

		// The first section of this method tests for valid inputs
		String error = "";

		// Checking if username exists already
		try {
			if (getCustomer(username) != null) {
				error += "Username already exists";
			}
		}
		catch (IllegalArgumentException e) {
		}

		// Checks if password is null or not long enough
		if (password == null || password.length() < 6) {
			error += "Password with 7 or more characters is required";
		}

		// Checks if email is valid **could be improved**
		if (email == null || email.equals("")) {
			error += "Non empty email must be provided";
		}

		// Checks if payment method is set correctly
		System.out.println(defaultPaymentMethod);
		if (!defaultPaymentMethod.equals("credit") && !defaultPaymentMethod.equals("paypal")) {
			error += "Default payment method must be set to 'credit' or 'paypal'";
		}

		// If an error is found, throw an exception
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		// Set the current date as creation date
		long millis = System.currentTimeMillis();
		Date creationDate = new java.sql.Date(millis);

		// Create a customer object and initialize all parameters
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.setDefaultPaymentMethod(Converters.convertStringToPaymentMethod(defaultPaymentMethod));
		customer.setCreationDate(creationDate);
		customer.setArtworksViewed(null);
		customer.setTransaction(null);
		// customer.setSmartGallery(smartGallery); ** Not sure how to do this **
		customerRepository.save(customer); // Save to customer repository
		return customer; // Return customer with updated parameters
	}

	/**
	 * @author roeywine Method that gets an existing customer given a username
	 * 
	 * @param username Username for the profile
	 * @return customer The customer given by username
	 */
	@Transactional
	public Customer getCustomer(String username) {

		if (username != null) {

			// Uses existing method in customer repository to find a customer by username
			Customer customer = customerRepository.findCustomerByUsername(username);

			// If the customer doesn't exist, throw an error
			if (customer == null) {
				String error = "Customer doesn't exist";
				throw new IllegalArgumentException(error);
			}

			// Otherwise return the found customer
			return customer;
		}

		// If the username input is null, return null
		else {
			return null;
		}
	}

	/**
	 * @author roeywine Method that gets all existing customers
	 * 
	 * @return customer list
	 */
	@Transactional
	public List<Customer> getAllCustomers() {

		// Uses existing method in customer repository to find all the existing
		// customers
		return toList(customerRepository.findAll());
	}

	/**
	 * @author roeywine Method that deletes a customer profile
	 * 
	 * @param username Username for the account being deleted
	 */
	public Customer deleteCustomer(String username) {

		if (username == null || username == "") {
			throw new IllegalArgumentException("Empty username was provided");
		}

		// Uses existing method in customer repository to find a customer by username
		Customer customer = customerRepository.findCustomerByUsername(username);

		// If the customer doesn't exist, throw an error
		if (customer == null) {
			String error = "Customer doesn't exist";
			throw new IllegalArgumentException(error);
		}

		// ** Will need to delete transactions
		// Delete customer from repository
		customerRepository.delete(customer);
		return customer;

	}

	/* **** ARTIST METHODS **** */

	/**
	 * @author roeywine Method that creates and saves an artist
	 * 
	 * @param username             Username for the profile
	 * @param password             Password for the profile
	 * @param email                Email to be used in the profile
	 * @param defaultPaymentMethod A default payment method for purchases
	 * @param isVerified           Whether or not the author is verified by the
	 *                             system
	 * @return artist The artist created
	 */
	@Transactional
	public Artist createArtist(String username, String password, String email, String defaultPaymentMethod) {

		// The first section of this method tests for valid inputs
		String error = "";

		// Checking if username exists already
		if (getArtist(username) != null) {
			error += "Username already exists";
		}

		// Checks if password is null or not long enough
		if (password == null || password.length() < 6) {
			error += "Password with 7 or more characters is required";
		}

		// Checks if email is valid **could be improved**
		if (email == null || email.equals("")) {
			error += "Non empty email must be provided";
		}

		// Checks if payment method is set correctly
		if (!defaultPaymentMethod.equals("credit") || !defaultPaymentMethod.equals("paypal")) {
			error += "Default payment method must be set to 'credit' or 'paypal'";
		}

		// If an error is found, throw an exception
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		// Set the current date as creation date
		long millis = System.currentTimeMillis();
		Date creationDate = new java.sql.Date(millis);

		// Create a customer object and initialize all parameters
		Artist artist = new Artist();
		artist.setUsername(username);
		artist.setPassword(password);
		artist.setEmail(email);
		artist.setDefaultPaymentMethod(Converters.convertStringToPaymentMethod(defaultPaymentMethod));
		artist.setCreationDate(creationDate);
		artist.setIsVerified(false); // When an artists profile is made, they are not verified initially
		artistRepository.save(artist); // Save to customer repository
		return artist; // Return artist with updated parameters
	}

	/**
	 * @author roeywine Method that gets an existing artist given a username
	 * 
	 * @param username Username for the profile
	 * @return artist The artist given by username
	 */
	@Transactional
	public Artist getArtist(String username) {

		if (username != null) {

			// Uses existing method in artist repository to find an artist by username
			Artist artist = artistRepository.findArtistByUsername(username);

			// If the artist doesn't exist, throw an error
			if (artist == null) {
				String error = "Artist doesn't exist";
				throw new IllegalArgumentException(error);
			}

			// Otherwise return the found artist
			return artist;
		}

		// If the username input is null, return null
		else {
			return null;
		}
	}

	/**
	 * @author roeywine Method that gets all existing artists
	 * 
	 * @return artist list
	 */
	@Transactional
	public List<Artist> getAllArtists() {

		// Uses existing method in customer repository to find all the existing
		// customers
		return toList(artistRepository.findAll());
	}

	/**
	 * @author roeywine Method to verify an artist
	 * 
	 * @param artist The artist being verified
	 */
	@Transactional
	public void verifyArtist(Artist artist) {
		artist.setIsVerified(true);
	}

	/**
	 * @author roeywine Method to unverify an artist
	 * 
	 * @param artist The artist being unverified
	 */
	@Transactional
	public void unverifyArtist(Artist artist) {
		artist.setIsVerified(false);
	}

	/**
	 * @author roeywine Method to get all verified artists
	 * 
	 * @return A list of all verified artists
	 */
	@Transactional
	public List<Artist> getAllVerifiedArtists() {
		return artistRepository.findArtistByIsVerified(true);
	}

	/**
	 * @author roeywine Method to get all non verified artists
	 * 
	 * @return A list of all non verified artists
	 */
	@Transactional
	public List<Artist> getAllNonVerifiedArtists() {
		return artistRepository.findArtistByIsVerified(false);
	}

	/**
	 * @author roeywine Method that deletes an artist profile
	 * 
	 * @param username Username for the account being deleted
	 */
	public Artist deleteArtist(String username) {

		if (username == null || username == "") {
			throw new IllegalArgumentException("Empty username was provided");
		}

		// Uses existing method in artist repository to find an artist by username
		Artist artist = artistRepository.findArtistByUsername(username);

		// If the artist doesn't exist, throw an error
		if (artist == null) {
			String error = "Artist doesn't exist";
			throw new IllegalArgumentException(error);
		}

		// ** Will need to delete transactions and artworks
		// Delete artist from repository
		artistRepository.delete(artist);
		
		return artist;

	}
	
	/**
	 * Log into a profile
	 * @param profile
	 */
	public void login(Profile profile) {
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		profile.login();
	}
	
	/**
	 * Log into a profile
	 * @param username
	 */
	public void login(String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		Artist artist = artistRepository.findArtistByUsername(username);
		Profile profile = null;
		if(customer != null) {
			profile = customer;
		} else if (artist != null) {
			profile = artist;
		}
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		profile.login();
	}
	
	/**
	 * Log out of a profile
	 * @param profile
	 */
	public void logout(Profile profile) {
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		profile.logout();
	}
	
	/**
	 * Log out of a profile
	 * @param username
	 */
	public void logout(String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		Artist artist = artistRepository.findArtistByUsername(username);
		Profile profile = null;
		if(customer != null) {
			profile = customer;
		} else if (artist != null) {
			profile = artist;
		}
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		profile.logout();
	}
	
	public void updateEmail(String oldEmail, String newEmail) {
		Profile profile = null;
		profile = customerRepository.findCustomerByEmail(oldEmail);
		if (profile == null) {
			profile = artistRepository.findArtistByEmail(oldEmail);
		}
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		profile.setEmail(newEmail);
		if (profile instanceof Customer) {
			customerRepository.save((Customer) profile);
		} else {
			artistRepository.save((Artist) profile);
		}
	}
	
	public void updatePassword(Profile profile, String password) {
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		profile.setPassword(password);
		if (profile instanceof Customer) {
			customerRepository.save((Customer) profile);
		} else {
			artistRepository.save((Artist) profile);
		}
	}

	// Helper method to retrieve lists of objects
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
