package ca.mcgill.ecse321.smartgallery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.HashSet;
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
	
	public Artist createArtist(Date date, String email, String password, SmartGallery smartGallery, PaymentMethod pm,
			String username) {
		Artist artist = new Artist();
		artist.setCreationDate(date);
		artist.setDefaultPaymentMethod(pm);
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
	
	public Listing createListing(int id, boolean isSold, Artwork artwork, Date dateListed,
			Gallery gallery) {
		Listing listing = new Listing();
		listing.setGallery(gallery);
		listing.setListingID(id);
		listing.setIsSold(isSold);
		listing.setArtwork(artwork);
		listing.setListedDate(dateListed);
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
		transaction.setSmartGallery(smartGallery);
		transactionRepository.save(transaction);
		return transaction;
	}
	
	public Artwork createArtwork(HashSet<Artist> artists, Gallery gallery, String name, int year, double price, boolean isBeingPromoted, ArtStyle style,
			int height, int weight, int width, int artwordID) {
		Artwork artwork = new Artwork();
		artwork.setArtists(artists);
		artwork.setGallery(gallery);
		artwork.setArtworkID(artwordID);
		artwork.setName(name);
		artwork.setYear(year);
		artwork.setPrice(price);
		artwork.setIsBeingPromoted(isBeingPromoted);
		artwork.setStyle(style);
		artwork.setHeight(height);
		artwork.setWeight(weight);
		artwork.setWidth(width);
		artworkRepository.save(artwork);
		return artwork;
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
	
	
	@Test
	public void testPersistAndLoadArtist() {
		
		int smartGalleryID = 12345;
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		String name = "aritistTest";
		 String str="2020-10-09";  
		Date date=Date.valueOf(str);//converting string into sql date  
		Artist artist = createArtist(date, "email", "password", smartGallery, PaymentMethod.CREDIT,
				name);
		
		artist = null;
		artist = artistRepository.findArtistByUsername(name);
		assertNotNull(artist);
		assertEquals(name, artist.getUsername());
	}
	
	@Test
	public void testPersistAndLoadListing() {
		int smartGalleryID = 12345;
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		String artistName = "artistTest";
		String str="2020-10-09";  
		Date date=Date.valueOf(str);//converting string into sql date  
		Artist artist = createArtist(date, "email", "password", smartGallery, PaymentMethod.CREDIT,
				artistName);
		String galleryName = "galleryName";
		Gallery gallery = createGallery(galleryName, smartGallery);
		HashSet<Artist> artists = new HashSet<Artist>();
		artists.add(artist);
		
		String username = "username";
		Date customerDate = new Date(20000);
		String email = "test@email.com";
		String password = "password";
		
		Customer customer = createCustomer(username, customerDate, email, password, smartGallery);
		
		String artworkName = "artwork";
		int year = 2000;
		double price = 10.0;
		int height = 10;
		int weight = 10;
		int width = 0;
		int artworkID = 123;
		Artwork artwork = createArtwork(artists, gallery, artworkName, year, price, false, ArtStyle.REALIST,
				height, weight, width, artworkID);
		
		int listingID = 12;
		String strListing = "2020-10-08";  
		Date listingDate = Date.valueOf(str);//converting string into sql date 
		System.out.println(artwork.getName());
		Listing listing = createListing(listingID, false, artwork, listingDate,
				gallery);
		System.out.println(listing.getArtwork().getName());
		
		listing = null;
		listing = listingRepository.findListingByListingID(listingID);
		assertNotNull(listing);
		assertEquals(listingID, listing.getListingID());
	}
		
	@Test
	public void testPersistAndLoadTransaction() {
		int smartGalleryID = 12345;
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		String artistName = "artistTest";
		String str="2020-10-09";  
		Date date=Date.valueOf(str);//converting string into sql date  
		Artist artist = createArtist(date, "email", "password", smartGallery, PaymentMethod.CREDIT,
				artistName);
		String galleryName = "galleryName";
		Gallery gallery = createGallery(galleryName, smartGallery);
		HashSet<Artist> artists = new HashSet<Artist>();
		artists.add(artist);
		
		String username = "username";
		Date customerDate = new Date(20000);
		String email = "test@email.com";
		String password = "password";
		
		Customer customer = createCustomer(username, customerDate, email, password, smartGallery);
		
		String artworkName = "artwork";
		int year = 2000;
		double price = 10.0;
		int height = 10;
		int weight = 10;
		int width = 0;
		int artworkID = 123;
		Artwork artwork = createArtwork(artists, gallery, artworkName, year, price, false, ArtStyle.REALIST,
				height, weight, width, artworkID);
		
		int listingID = 12;
		String strListing = "2020-10-08";  
		Date listingDate = Date.valueOf(str);//converting string into sql date 
		System.out.println(artwork.getName());
		Listing listing = createListing(listingID, false, artwork, listingDate,
				gallery);
		System.out.println(listing.getArtwork().getName());
		
		int transactionID = 1234;
		HashSet<Profile> profiles = new HashSet<Profile>();
		profiles.add(artist);
		profiles.add(customer);
		Date paymentDate = Date.valueOf(str);//converting string into sql date
		Transaction transaction = createTransaction(transactionID, PaymentMethod.CREDIT, DeliveryMethod.PICKUP, smartGallery, 
				profiles, paymentDate, listing);
		transaction = null;
		transaction = transactionRepository.findTransactionByTransactionID(transactionID);
		assertNotNull(transaction);
		assertEquals(transactionID, transaction.getTransactionID());
	}
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
	@Test
	public void testPersistAndLoadCustomer() {
		int smartGalleryID = 12345;
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		
		String galleryName = "galleryName";
		Gallery gallery = createGallery(galleryName, smartGallery);
		
		String username = "username";
		Date date = new Date(20000);
		String email = "test@email.com";
		String password = "password";
		
		Customer customer = createCustomer(username, date, email, password, smartGallery);
		
		customer = null;
		
		customer = customerRepository.findCustomerByUsername(username);
		assertNotNull(customer);
		assertEquals(username, customer.getUsername());
	}
}
	
