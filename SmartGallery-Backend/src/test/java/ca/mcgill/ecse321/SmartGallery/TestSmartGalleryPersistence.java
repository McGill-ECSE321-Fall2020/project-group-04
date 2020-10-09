package ca.mcgill.ecse321.SmartGallery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	
	@Test
	public void testPersistAndLoadSmartGallery() {
		int id = 12345;
		SmartGallery smartGallery = new SmartGallery();
		smartGallery.setSmartGalleryID(id);
		smartGalleryRepository.save(smartGallery);
		smartGallery = null;
		smartGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(id);
		assertNotNull(smartGallery);
		assertEquals(id, smartGallery.getSmartGalleryID());
	}
	
	@Test
	public void testPersistAndLoadGallery() {
		String name = "TestGallery";
		Gallery gallery = new Gallery();
		gallery.setGalleryName(name);
		galleryRepository.save(gallery);
		gallery = null;
		gallery = galleryRepository.findGalleryByGalleryName(name);
		assertNotNull(gallery);
		assertEquals(name, gallery.getGalleryName());
	}
	
	@Test
	public void testPersistAndLoadArtist() {
		String name = "TestArtist";
		Artist artist = new Artist();
		artist.setUsername(name);
		artistRepository.save(artist);
		artist = null;
		artist = artistRepository.findArtistByUsername(name);
		assertNotNull(artist);
		assertEquals(name, artist.getUsername());
	}
	
	@Test
	public void testPersistAndLoadListing() {
		int id = 12345;
		Listing listing = new Listing();
		listing.setListingID(id);
		listingRepository.save(listing);
		listing = null;
		listing = listingRepository.findListingByListingID(id);
		assertNotNull(listing);
		assertEquals(id, listing.getListingID());
	}
	
	@Test
	public void testPersistAndLoadTransaction() {
		int id = 12345;
		Transaction transaction = new Transaction();
		transaction.setTransactionID(id);
		transactionRepository.save(transaction);
		transaction = null;
		transaction = transactionRepository.findTransactionByTransactionID(id);
		assertNotNull(transaction);
		assertEquals(id, transaction.getTransactionID());
	}
	
	@Test
	public void testPersistAndLoadArtwork() {
		int id = 12345;
		Artwork artwork = new Artwork();
		artwork.setArtworkID(id);
		artworkRepository.save(artwork);
		artwork = null;
		artwork = artworkRepository.findArtworkByArtworkID(id);
		assertNotNull(artwork);
		assertEquals(id, artwork.getArtworkID());
	}
	
	@Test
	public void testPersistAndLoadCustomer() {
		String name = "TestCustomer";
		Customer customer = new Customer();
		customer.setUsername(name);
		customerRepository.save(customer);
		customer = null;
		customer = customerRepository.findCustomerByUsername(name);
		assertNotNull(customer);
		assertEquals(name, customer.getUsername());
	}
}
	
