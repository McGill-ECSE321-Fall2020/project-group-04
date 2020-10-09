package ca.mcgill.ecse321.smartgallery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.smartgallery.dao.*;
import ca.mcgill.ecse321.smartgallery.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestSmartGalleryPersistence {
	
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private ArtworkRepository artworkRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private ListingRepository listingRepository;
	@Autowired
	private SmartGalleryRepository smartGalleryRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	@AfterEach
	public void clearDatabase() {
		smartGalleryRepository.deleteAll();
		galleryRepository.deleteAll();
		artistRepository.deleteAll();
		listingRepository.deleteAll();
		transactionRepository.deleteAll();
		artworkRepository.deleteAll();
		customerRepository.deleteAll();
	}
	
	public SmartGallery createSmartGallery(int id) {
		SmartGallery smartGallery = new SmartGallery();
		smartGallery.setSmartGalleryID(id);
		smartGalleryRepository.save(smartGallery);
		return smartGallery;
	}
	
	public Gallery createGallery(String name, SmartGallery smartGallery) {
		Gallery gallery = new Gallery();
		gallery.setGalleryName(name);
		gallery.setSmartGallery(smartGallery);
		galleryRepository.save(gallery);
		return gallery;
	}
	
	public Artist createArtist(Date date, String email, String password, SmartGallery smartGallery,
			String username) {
		Artist artist = new Artist();
		artist.setCreationDate(date);
		artist.setEmail(email);
		artist.setIsVerified(false);
		artist.setPassword(password);
		artist.setSmartGallery(smartGallery);
		artist.setUsername(username);
		artistRepository.save(artist);
		return artist;
	}
	
	public Customer createCustomer(String username, Date date, String email, String password, 
			SmartGallery smartGallery) {
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setCreationDate(date);
		customer.setDefaultPaymentMethod(null);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setSmartGallery(smartGallery);
		customerRepository.save(customer);
		return customer;
	}
	
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
		listingRepository.save(listing);
		return listing;
	}
	
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
		transactionRepository.save(transaction);
		return transaction;
	}
	
	@Test
	public void testPersistAndLoadSmartGallery() {
		int smartGalleryID = 12345;
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		
		smartGallery = null;
		
		smartGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID);	
		
		assertNotNull(smartGallery);
		assertEquals(smartGalleryID, smartGallery.getSmartGalleryID());
	}
	
	@Test
	public void testPersistAndLoadGallery() {
		int smartGalleryID = 12345;
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		
		String galleryName = "galleryName";
		Gallery gallery = createGallery(galleryName, smartGallery);
		
		gallery = null;
		
		gallery = galleryRepository.findGalleryByGalleryName(galleryName);
		assertNotNull(gallery);
		assertEquals(galleryName, gallery.getGalleryName());
	}
	
//	@Test
//	public void testPersistAndLoadArtist() {
//		String name = "TestArtist";
//		Artist artist = new Artist();
//		artist.setUsername(name);
//		artistRepository.save(artist);
//		artist = null;
//		artist = artistRepository.findArtistByUsername(name);
//		assertNotNull(artist);
//		assertEquals(name, artist.getUsername());
//	}
//	
//	@Test
//	public void testPersistAndLoadListing() {
//		int id = 12345;
//		Listing listing = new Listing();
//		listing.setListingID(id);
//		listingRepository.save(listing);
//		listing = null;
//		listing = listingRepository.findListingByListingID(id);
//		assertNotNull(listing);
//		assertEquals(id, listing.getListingID());
//	}
//	
//	@Test
//	public void testPersistAndLoadTransaction() {
//		int id = 12345;
//		Transaction transaction = new Transaction();
//		transaction.setTransactionID(id);
//		transactionRepository.save(transaction);
//		transaction = null;
//		transaction = transactionRepository.findTransactionByTransactionID(id);
//		assertNotNull(transaction);
//		assertEquals(id, transaction.getTransactionID());
//	}
//	
//	@Test
//	public void testPersistAndLoadArtwork() {
//		int id = 12345;
//		Artwork artwork = new Artwork();
//		artwork.setArtworkID(id);
//		artworkRepository.save(artwork);
//		artwork = null;
//		artwork = artworkRepository.findArtworkByArtworkID(id);
//		assertNotNull(artwork);
//		assertEquals(id, artwork.getArtworkID());
//	}
//	
//	@Test
//	public void testPersistAndLoadCustomer() {
//		String name = "TestCustomer";
//		Customer customer = new Customer();
//		customer.setUsername(name);
//		customerRepository.save(customer);
//		customer = null;
//		customer = customerRepository.findCustomerByUsername(name);
//		assertNotNull(customer);
//		assertEquals(name, customer.getUsername());
//	}
}
	
