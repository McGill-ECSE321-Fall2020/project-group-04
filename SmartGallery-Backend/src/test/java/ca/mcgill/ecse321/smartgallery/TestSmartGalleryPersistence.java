package ca.mcgill.ecse321.smartgallery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.HashSet;

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
		artwork.setListing(listing);
		listing.setListedDate(dateListed);
		listingRepository.save(listing);
		return listing;
	}
	
	public Transaction createTransaction(int transactionID, PaymentMethod paymentMethod, DeliveryMethod deliveryMethod, SmartGallery smartGallery, 
			Profile profile, Date paymentDate, Listing listing)
	{
		Transaction transaction = new Transaction();
		transaction.setTransactionID(transactionID);
		transaction.setPaymentMethod(paymentMethod);
		transaction.setDeliveryMethod(deliveryMethod);
		transaction.setProfile(profile);
		transaction.setPaymentDate(paymentDate);
		transaction.setListing(listing);
		transaction.setSmartGallery(smartGallery);
		transactionRepository.save(transaction);
		return transaction;
	}
	
	public Artwork createArtwork(HashSet<Artist> artists, Gallery gallery, String name, int year, double price, boolean isBeingPromoted, ArtStyle style,
			int height, int weight, int width, int artworkID) {
		Artwork artwork = new Artwork();
		artwork.setArtists(artists);
		artwork.setGallery(gallery);
		artwork.setArtworkID(artworkID);
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
		assertEquals(gallery.getSmartGallery().getSmartGalleryID(), smartGallery.getSmartGalleryID());
		
		smartGallery = null;
		smartGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID);
		assertEquals(smartGallery.getGallery().getGalleryName(), galleryName);
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
		assertEquals(artist.getSmartGallery().getSmartGalleryID(), smartGallery.getSmartGalleryID());
	}
	
	@Test
	public void testPersistAndLoadListing() {
		int listingID = 123;

		Date date=Date.valueOf("2020-10-09"); // converting string into sql date  
		SmartGallery smartGallery = createSmartGallery(12345);
		String galleryName = "gallery";
		Gallery gallery = createGallery(galleryName, smartGallery);
		String artistName = "artistName";
		Artist artist = createArtist(date, "email", "password", smartGallery, PaymentMethod.CREDIT,
				artistName);
		HashSet<Artist> artists = new HashSet<Artist>();
		artists.add(artist);
		
		String artworkName = "artwork";
		int year = 2000;
		double price = 10.0;
		int height = 10;
		int weight = 10;
		int width = 0;
		int artworkID = 123;
		Artwork artwork = createArtwork(artists, gallery, artworkName, year, price, false, ArtStyle.REALIST,
				height, weight, width, artworkID);
		
		String strListing = "2020-10-08";  
		Date listingDate = Date.valueOf(strListing);//converting string into sql date 
		Listing listing = createListing(listingID, false, artwork, listingDate,
				gallery);
		
		System.out.println(artwork.getListing().getListingID());
		
		listing = null;
		listing = listingRepository.findListingByListingID(listingID);
		listing.getArtwork().getName();
		assertNotNull(listing);
		assertEquals(listingID, listing.getListingID());
		assertEquals(listing.getGallery().getGalleryName(), gallery.getGalleryName());
		
		gallery = null;
		gallery = galleryRepository.findGalleryByGalleryName(galleryName);
		assertEquals(listing.getGallery().getGalleryName(), galleryName);
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
		Date listingDate = Date.valueOf(strListing);//converting string into sql date 
		Listing listing = createListing(listingID, false, artwork, listingDate,
				gallery);
		
		artwork = null;
		artwork = artworkRepository.findArtworkByName(artworkName).get(0);
		System.out.println(artwork.getListing().getListingID());		
		
		int transactionID = 1234;
		Date paymentDate = Date.valueOf(str);//converting string into sql date
		Transaction transaction = createTransaction(transactionID, PaymentMethod.CREDIT, DeliveryMethod.PICKUP, smartGallery, 
				customer, paymentDate, listing);
		transaction = null;
		transaction = transactionRepository.findTransactionByTransactionID(transactionID);
		
		assertNotNull(transaction);
		assertEquals(transactionID, transaction.getTransactionID());
		assertEquals(transaction.getListing().getListingID(), listing.getListingID());
	}	
	
	@Test
	public void testPersistAndLoadArtwork() {
		int id = 12345;
		
		SmartGallery smartGallery = createSmartGallery(12345);
		Gallery gallery = createGallery("galleryName", smartGallery);
		Date date=Date.valueOf("2020-10-09");
		Artist artist = createArtist(date, "email", "password", smartGallery, PaymentMethod.CREDIT,
				"aritistTest");
		HashSet<Artist> set = new HashSet<>();
		set.add(artist);
		
		Artwork artwork = createArtwork(set, gallery, "artworkName", 2000, 5.0, true, ArtStyle.IMPRESSIONIST, 5, 4, 3, id);
		artwork = null;
		artwork = artworkRepository.findArtworkByArtworkID(id);
		assertNotNull(artwork);
		assertEquals(id, artwork.getArtworkID());
		assertEquals(gallery.getGalleryName(), artwork.getGallery().getGalleryName());
	}
	
	@Test
	public void testPersistAndLoadCustomer() {
		int smartGalleryID = 12345;
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		
		String username = "username";
		Date date = new Date(20000);
		String email = "test@email.com";
		String password = "password";
		
		Customer customer = createCustomer(username, date, email, password, smartGallery);
		
		customer = null;
		
		customer = customerRepository.findCustomerByUsername(username);
		assertNotNull(customer);
		assertEquals(username, customer.getUsername());
		assertEquals(smartGallery.getSmartGalleryID(), customer.getSmartGallery().getSmartGalleryID());
	}
}
	
