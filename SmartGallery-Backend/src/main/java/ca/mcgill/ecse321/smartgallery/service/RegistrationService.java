package ca.mcgill.ecse321.smartgallery.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.Profile;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@Service
public class RegistrationService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	private SmartGalleryRepository smartGalleryRepository;

	@Autowired
	private ArtworkRepository artworkRepository;

	@Autowired
	private TransactionRepository transactionRepository;

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
	public Customer createCustomer(String username, String password, String email, PaymentMethod defaultPaymentMethod,
			SmartGallery smartGallery) {

		// The first section of this method tests for valid inputs
		String error = "";

		if (username == null || username.equals("")) {
			error += "Non empty username must be provided";
		}

		// If an error is found, throw an exception
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		if (!checkExistingUsernameAndEmail(username, email)) {
			error += "This username/email have already been used";
		}

		// Checks if password is null or not long enough
		if (password == null || password.length() < 6) {
			error += "Password with 7 or more characters is required";
		}

		// Checks if email is valid **could be improved**
		if (email == null || !validateEmail(email)) {
			error += "Invalid email format";
		}

		// Checks if payment method is set correctly
		if (defaultPaymentMethod == null || (!defaultPaymentMethod.name().equalsIgnoreCase("credit")
				&& !defaultPaymentMethod.name().equalsIgnoreCase("paypal"))) {

			error += "Default payment method must be set to 'credit' or 'paypal'";
		}

		// If an error is found, throw an exception
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		// Remove spaces
		username = username.trim();
		// Set the current date as creation date
		long millis = System.currentTimeMillis();
		Date creationDate = new java.sql.Date(millis);

		// Create a customer object and initialize all parameters
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.setDefaultPaymentMethod(defaultPaymentMethod);
		customer.setCreationDate(creationDate);
		customer.setArtworksViewed(null);
		customer.setTransaction(null);
		customer.setSmartGallery(smartGallery);
		smartGalleryRepository.save(smartGallery);
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

		if (username == null || username.equals("")) {
			throw new IllegalArgumentException("Username is empty");
		}

		// Uses existing method in customer repository to find a customer by username
		Customer customer = customerRepository.findCustomerByUsername(username);

		// If the customer doesn't exist, throw an error
		if (customer == null) {
			throw new IllegalArgumentException("Customer doesn't exist");
		}

		// Otherwise return the found customer
		return customer;

	}

	@Transactional
	public Customer getCustomerByEmail(String email) {
		if (email == null || email == "") {
			throw new IllegalArgumentException("Empty email was provided");
		}

		if (!validateEmail(email)) {
			throw new IllegalArgumentException("Invalid email format");
		}

		Customer customer = customerRepository.findCustomerByEmail(email);

		if (customer == null) {
			throw new IllegalArgumentException("Customer with this email doesn't exist");
		}
		return customer;
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
	@Transactional
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

		if (!customer.isLoggedIn()) {
			throw new IllegalArgumentException("Customer must be logged in");
		}
		// ** Will need to delete transactions
		Set<Transaction> transactionDeleting = customer.getTransaction();
		if (transactionDeleting != null) {
			for (Transaction t : transactionDeleting) {
				transactionRepository.delete(t);
			}
		}
		customer.setArtworksViewed(null);
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
	public Artist createArtist(String username, String password, String email, PaymentMethod defaultPaymentMethod,
			SmartGallery smartGallery) {

		// The first section of this method tests for valid inputs
		String error = "";

		if (username == null || username.equals("")) {
			error += "Non empty username must be provided";
		}

		// If an error is found, throw an exception
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		// Checking if username exists already
		if (!checkExistingUsernameAndEmail(username, email)) {
			error += "This username/email have already been used";
		}

		// Checks if password is null or not long enough
		if (password == null || password.length() < 6) {
			error += "Password with 7 or more characters is required";
		}

		// Checks if email is valid
		if (email == null || !validateEmail(email)) {
			error += "Invalid email format";
		}

		// Checks if payment method is set correctly
		if (defaultPaymentMethod == null || (!defaultPaymentMethod.name().equalsIgnoreCase("credit")
				&& !defaultPaymentMethod.name().equalsIgnoreCase("paypal"))) {
			error += "Default payment method must be set to 'credit' or 'paypal'";
		}

		// If an error is found, throw an exception
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		// Remove spaces
		username = username.trim();
		// Set the current date as creation date
		long millis = System.currentTimeMillis();
		Date creationDate = new java.sql.Date(millis);

		// Create a customer object and initialize all parameters
		Artist artist = new Artist();
		artist.setUsername(username);
		artist.setPassword(password);
		artist.setEmail(email);
		artist.setDefaultPaymentMethod(defaultPaymentMethod);
		artist.setCreationDate(creationDate);
		artist.setIsVerified(false); // When an artists profile is made, they are not verified initially
		artist.setSmartGallery(smartGallery);
		HashSet<Profile> pSet = new HashSet<>();
		if (smartGallery.getProfile() != null) {
			pSet = new HashSet<>(smartGallery.getProfile());
		}
		pSet.add(artist);
		smartGallery.setProfile(pSet);
		smartGalleryRepository.save(smartGallery);
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

		if (username == null || username.equals("")) {
			throw new IllegalArgumentException("Username is empty");
		}

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
	@Transactional
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

		if (!artist.isLoggedIn()) {
			throw new IllegalArgumentException("Artist must be logged in");
		}

		// Delete all the artist's artworks
		Set<Artwork> artworksDeleting = artist.getArtworks();
		if (artworksDeleting != null) {
			for (Artwork artwork : artworksDeleting) {
				if (artwork.getListing() == null && artwork.getArtists().size() > 1) {
					artist.getArtworks().remove(artwork);
					artworkRepository.delete(artwork);
				} else {
					artwork.getArtists().remove(artist);
				}
			}
		}

		// Delete all the artist's artworks
		artist.setArtworksViewed(null);
		Set<Transaction> transactionDeleting = artist.getTransaction();
		if (transactionDeleting != null) {
			for (Transaction t : transactionDeleting) {
				transactionRepository.delete(t);
			}
		}

		// Delete artist from repository
		artistRepository.delete(artist);

		return artist;

	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	@Transactional
	public Profile getProfile(String username) {
		if (username == null || username.equals("")) {
			throw new IllegalArgumentException("Username is empty");
		}

		// Uses existing method in artist repository to find an artist by username
		Profile p = artistRepository.findArtistByUsername(username);

		if (p == null) {
			p = customerRepository.findCustomerByUsername(username);
		}

		if (p == null) {
			throw new IllegalArgumentException("Profile doesn't exist");
		}
		return p;
	}

	/**
	 * Log into a profile
	 * 
	 * @param profile
	 */
	@Transactional
	public boolean login(Profile profile, String password) {
		if (profile == null) {
			String error = "Profile doesn't exist";
			return false;
//			throw new IllegalArgumentException(error);
		}
		if (profile.getPassword().equals(password)) {
			profile.login();
			return true;
		} else {
			return false;
//          String error = "Incorrect password";
//          throw new IllegalArgumentException(error);
		}
	}

	/**
	 * Log out of a profile
	 * 
	 * @param profile
	 */
	@Transactional
	public void logout(Profile profile) {
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		profile.logout();
	}

	@Transactional
	public boolean updateEmail(Profile profile, String newEmail, String password) {
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		if (profile.getPassword().equals(password)) {
			profile.setEmail(newEmail);
			return true;
		} else
			return false;
	}

	/**
	 * Update the password of a profile
	 * 
	 * @param profile
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Transactional
	public boolean updatePassword(Profile profile, String oldPassword, String newPassword) {
		if (profile == null) {
			String error = "Profile doesn't exist";
			throw new IllegalArgumentException(error);
		}
		if (profile.getPassword().equals(oldPassword)) {
			profile.setPassword(newPassword);
			return true;
		} else
			return false;
	}

	@Transactional
	public Artist getArtistByEmail(String email) {
		if (email == null || email == "") {
			throw new IllegalArgumentException("Empty email was provided");
		}

		if (!validateEmail(email)) {
			throw new IllegalArgumentException("Invalid email format");
		}

		Artist artist = artistRepository.findArtistByEmail(email);
		if (artist == null) {
			throw new IllegalArgumentException("Artist with this email doesn't exist");
		}
		return artist;
	}

	// ValidateParameters

	private boolean checkExistingUsernameAndEmail(String username, String email) {
		boolean user = artistRepository.findArtistByUsername(username) == null
				&& customerRepository.findCustomerByUsername(username) == null;
		boolean validEmail = artistRepository.findArtistByEmail(email) == null
				&& customerRepository.findCustomerByEmail(email) == null;
		return user && validEmail;
	}

	private boolean validateEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
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
