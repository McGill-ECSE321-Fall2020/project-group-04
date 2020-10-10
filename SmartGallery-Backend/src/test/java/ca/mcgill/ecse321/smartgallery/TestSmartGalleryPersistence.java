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
	
/*
	@Test
	public void testPersistAndLoadListing() {
		int smartGalleryID = 12345;
		String galleryName = "gallery";
		String str="2020-10-09";  
		Date date=Date.valueOf(str);//converting string into sql date  
		
		SmartGallery smartGallery = createSmartGallery(smartGalleryID);
		Gallery gallery = createGallery(galleryName, smartGallery);
		Artist artist = createArtist(date, "email", "password", smartGallery, PaymentMethod.CREDIT,
				"name");
		HashSet<Artist> artists = new HashSet<>();
		artists.add(artist);
		Artwork artwork = createArtwork(artists, gallery, "artwork", 2020, 500.0, false,ArtStyle.REALIST,50,
			50, 80, 70);
		Date dateListed = Date.valueOf(str);
		int listingID = 123;
		Listing listing = createListing(listingID, false, artwork, dateListed,gallery);
		listing = null;
		listing = listingRepository.findListingByListingID(listingID);
		assertNotNull(listing);
		assertEquals(listingID, listing.getListingID());
	}
	*/
	
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
	
