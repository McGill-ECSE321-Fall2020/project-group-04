package ca.mcgill.ecse321.smartgallery.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.smartgallery.dao.*;
import ca.mcgill.ecse321.smartgallery.model.*;

@ExtendWith(MockitoExtension.class)
public class BrowsingServiceTests {

	@Mock
	private SmartGalleryRepository smartGalleryDao;

	@Mock
	private GalleryRepository galleryDao;

	@Mock
	private ArtworkRepository artworkDao;

	@Mock
	private ListingRepository listingDao;

	@Mock
	private ArtistRepository artistDao;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private BrowsingService service;

	// SmartGallery variables
	private static final int SMARTGALLERY_KEY = 12345;
	private static final int NONEXISTING_SMARTGALLERY_KEY = 00000;

	// Gallery variables
	private static final String GALLERY_KEY = "TestGallery";
	private static final String NONEXISTING_GALLERY_KEY = "NotAGallery";

	// Artwork variables
	private static String ARTWORK_NAME = "artwork";
	private static final boolean PROMOTED = true;
	private static final ArtStyle STYLE = ArtStyle.IMPRESSIONIST;
	static final int A_ID = 393939;

	// Listing variables
	private static final boolean SOLD = true;
	private static final boolean NOT_SOLD = false;
	private static final int L_ID = 321;
	private static final int L_ID2 = 9393;

	// Artist variables
	private static final String ARTIST_USERNAME = "testartist";

	// Artworks for getAllPromoted
	Artwork artwork1 = new Artwork();
	Artwork artwork2 = new Artwork();
	Artwork artwork3 = new Artwork();

	// Customer Variables
	private static final String CUSTOMER_USERNAME = "testcustomer";
	private static final String CUSTOMER_EMAIL = "testcustomer@email.com";
	private static final String CUSTOMER_PASSWORD = "testcpass";
	private static final Date CUSTOMER_CREATION_DATE = Date.valueOf("2020-09-01");
	private static final PaymentMethod DEFAULTPAY = PaymentMethod.CREDIT;

	@BeforeEach
	public void setMockOutput() {
		lenient().when(smartGalleryDao.findSmartGalleryBySmartGalleryID(anyInt()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(SMARTGALLERY_KEY)) {
						SmartGallery smartGallery = new SmartGallery();
						smartGallery.setSmartGalleryID(SMARTGALLERY_KEY);
						return smartGallery;
					} else {
						return null;
					}
				});

		lenient().when(smartGalleryDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<SmartGallery> smartGalleries = new ArrayList<>();
			SmartGallery smartGallery = new SmartGallery();
			smartGallery.setSmartGalleryID(SMARTGALLERY_KEY);
			smartGalleries.add(smartGallery);
			return smartGalleries;
		});

		lenient().when(galleryDao.findGalleryByGalleryName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(GALLERY_KEY)) {
				Gallery gallery = new Gallery();
				gallery.setGalleryName(GALLERY_KEY);
				return gallery;
			} else {
				return null;
			}
		});

		lenient().when(galleryDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Gallery> galleries = new ArrayList<>();
			Gallery gallery = new Gallery();
			gallery.setGalleryName(GALLERY_KEY);
			galleries.add(gallery);
			return galleries;
		});

		lenient().when(artistDao.findArtistByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARTIST_USERNAME)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME);
				return artist;
			} else {
				return null;
			}
		});

		lenient().when(listingDao.findListingByListingID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(L_ID)) {
				Listing listing = new Listing();
				listing.setIsSold(NOT_SOLD);
				listing.setListingID(L_ID);
				return listing;
			} else if (invocation.getArgument(0).equals(L_ID2)) {
				Listing listing = new Listing();
				listing.setIsSold(SOLD);
				listing.setListingID(L_ID);
				return listing;
			} else {
				return null;
			}
		});

		lenient().when(artworkDao.findArtworkByIsBeingPromoted(anyBoolean()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(true)) {
						ArrayList<Artwork> artworks = new ArrayList<Artwork>();
						artworks.add(artwork1);
						artworks.add(artwork2);
						return artworks;
					} else {
						return null;
					}
				});

		lenient().when(artworkDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(A_ID)) {
				Artwork artwork = new Artwork();
				artwork.setArtworkID(A_ID);
				artwork.setIsBeingPromoted(PROMOTED);
				artwork.setStyle(STYLE);
				artwork.setName(ARTWORK_NAME);
				return artwork;
			} else {
				return null;
			}
		});

		// Customer creation
		lenient().when(customerRepository.findCustomerByUsername(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(CUSTOMER_USERNAME)) {
						Customer customer = new Customer();
						customer.setUsername(CUSTOMER_USERNAME);
						customer.setPassword(CUSTOMER_PASSWORD);
						customer.setEmail(CUSTOMER_EMAIL);
						customer.setCreationDate(CUSTOMER_CREATION_DATE);
						customer.setDefaultPaymentMethod(DEFAULTPAY);
						return customer;
					} else {
						return null;
					}
				});

		// Whenever anything is saved, just return the parameter object
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(smartGalleryDao.save(any(SmartGallery.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(galleryDao.save(any(Gallery.class))).thenAnswer(returnParameterAsAnswer);
	}

	/*
	 * ======================================================== SMARTGALLERY TESTS
	 * ========================================================
	 */

	@Test
	public void testCreateSmartGallery() {
		int key = 2325;
		SmartGallery smartGallery = null;
		try {
			smartGallery = service.createSmartGallery(key);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(smartGallery);
		assertEquals(key, smartGallery.getSmartGalleryID());
	}

	@Test
	public void testCreateSmartGalleryAlreadyExists() {
		String error = null;
		SmartGallery smartGallery = null;
		try {
			smartGallery = service.createSmartGallery(SMARTGALLERY_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("A smartGallery with that ID already exists", error);
		assertNull(smartGallery);
	}

	@Test
	public void testCreateSmartGalleryIdIsZero() {
		int ID = 0;
		String error = null;
		SmartGallery smartGallery = null;
		try {
			smartGallery = service.createSmartGallery(ID);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(smartGallery);
		// check error
		assertEquals("SmartGallery ID must not be zero", error);
	}

	@Test
	public void testGetSmartGallery() {
		try {
			service.getSmartGalleryByID(SMARTGALLERY_KEY).getSmartGalleryID();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(SMARTGALLERY_KEY, service.getSmartGalleryByID(SMARTGALLERY_KEY).getSmartGalleryID());
	}

	@Test
	public void testGetNonExistingSmartGallery() {
		String error = null;
		try {
			service.getSmartGalleryByID(NONEXISTING_SMARTGALLERY_KEY);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("SmartGallery doesn't exist", error);
	}

	@Test
	public void testGetAllSmartGalleries() {
		SmartGallery smartGallery = new SmartGallery();
		smartGallery.setSmartGalleryID(SMARTGALLERY_KEY);
		List<SmartGallery> smartGalleries = new ArrayList<>();
		try {
			smartGalleries = service.getAllSmartGalleries();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(smartGallery.getSmartGalleryID(), smartGalleries.get(0).getSmartGalleryID());
	}

	/*
	 * ======================================================= GALLERY TESTS
	 * =======================================================
	 */

	@Test
	public void testCreateGallery() {
		String key = "test1";
		Gallery gallery = null;
		try {
			gallery = service.createGallery(key, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}
		assertNotNull(gallery);
		assertEquals(key, gallery.getGalleryName());
	}

	@Test
	public void testCreateGalleryAlreadyExists() {
		String error = null;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(GALLERY_KEY, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("A gallery with that name already exists", error);
		assertNull(gallery);
	}

	@Test
	public void testCreateGalleryNameIsNull() {
		String galleryName = null;
		String error = null;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(galleryName, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery Name cannot be empty", error);
	}

	@Test
	public void testCreateGalleryNameIsEmpty() {
		String galleryName = "";
		String error = null;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(galleryName, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery Name cannot be empty", error);
	}

	@Test
	public void testCreateGalleryNameIsSpaces() {
		String galleryName = " ";
		String error = null;
		Gallery gallery = null;
		try {
			gallery = service.createGallery(galleryName, new SmartGallery(), 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(gallery);
		// check error
		assertEquals("Gallery Name cannot be empty", error);
	}

	@Test
	public void testCreateGallerySmartGalleryIsNull() {
		Gallery gallery = null;
		String error = null;
		try {
			gallery = service.createGallery("galleryTest1", null, 20);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(gallery);
		assertEquals("A SmartGallery is need to create a Gallery", error);
	}

	@Test
	public void testCreateGalleryCommisionSmallerThanZero() {
		Gallery gallery = null;
		String error = null;
		try {
			gallery = service.createGallery("galleryTest2", new SmartGallery(), -2);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(gallery);
		assertEquals("The commission percentage must be between 0 and 100 inclusive", error);
	}

	@Test
	public void testCreateGalleryCommisionLargerThan100() {
		Gallery gallery = null;
		String error = null;
		try {
			gallery = service.createGallery("galleryTest3", new SmartGallery(), 105);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(gallery);
		assertEquals("The commission percentage must be between 0 and 100 inclusive", error);
	}

	@Test
	public void testGetGalleryByName() {
		try {
			service.getGalleryByName(GALLERY_KEY).getGalleryName();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(GALLERY_KEY, service.getGalleryByName(GALLERY_KEY).getGalleryName());
	}

	@Test
	public void testGetNonExistingGallery() {
		String error = null;
		try {
			service.getGalleryByName(NONEXISTING_GALLERY_KEY);
			assertNull(service.getGalleryByName(NONEXISTING_GALLERY_KEY));
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Gallery doesn't exist", error);

	}

	@Test
	public void testGetGalleryByNameNameNull() {
		String error = null;
		try {
			service.getGalleryByName(null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Gallery Name cannot be empty", error);
	}

	@Test
	public void testGetAllGalleries() {
		Gallery gallery = new Gallery();
		gallery.setGalleryName(GALLERY_KEY);
		List<Gallery> galleries = new ArrayList<>();
		try {
			galleries = service.getAllGalleries();
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(gallery.getGalleryName(), galleries.get(0).getGalleryName());
	}

	/*
	 * ========================================================= PROMOTED ARTWORK
	 * TESTS =========================================================
	 */

	@Test
	public void testPromoteArtwork() {
		Artwork artwork = new Artwork();
		try {
			service.promoteArtwork(artwork);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertTrue(artwork.isIsBeingPromoted());
	}

	@Test
	public void testPromoteArtworkArtworkNull() {
		Artwork artwork = null;
		String error = null;
		try {
			service.promoteArtwork(artwork);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Artwork doesn't exist", error);
	}

	@Test
	public void testUnpromoteArtwork() {
		Artwork artwork = new Artwork();
		try {
			service.unpromoteArtwork(artwork);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertFalse(artwork.isIsBeingPromoted());
	}

	@Test
	public void testUnpromoteArtworkArtworkNull() {
		Artwork artwork = null;
		String error = null;
		try {
			service.unpromoteArtwork(artwork);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Artwork doesn't exist", error);
	}

	@Test
	public void testGetAllPromotedArtworks() {
		try {
			service.promoteArtwork(artwork1);
			service.promoteArtwork(artwork2);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(2, service.getAllPromotedArtworks().size());
		assertTrue(service.getAllPromotedArtworks().contains(artwork1));
		assertTrue(service.getAllPromotedArtworks().contains(artwork2));
		assertFalse(service.getAllPromotedArtworks().contains(artwork3));
	}

	/*
	 * ========================================================= SEARCHARTISTS TESTS
	 * =========================================================
	 */

	@Test
	public void testSearchArtists() {
		Artist artist1 = new Artist();
		artist1.setUsername("Jonathan");
		Artist artist2 = new Artist();
		artist2.setUsername("Jonas");
		Artist artist3 = new Artist();
		artist3.setUsername("Juan");
		ArrayList<Artist> artists = new ArrayList<>();
		artists.add(artist1);
		artists.add(artist2);
		artists.add(artist3);
		String searchInput = "jon";
		HashSet<Artist> searchResults = new HashSet<Artist>();
		try {
			searchResults = service.searchArtist(artists, searchInput);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(searchResults.size(), 2);
		assertTrue(searchResults.contains(artist1));
		assertTrue(searchResults.contains(artist2));
		assertFalse(searchResults.contains(artist3));
	}

	@Test
	public void testSearchArtistsArtistsNull() {
		ArrayList<Artist> artists = null;
		String searchInput = "jon";
		HashSet<Artist> searchResults = new HashSet<Artist>();
		String error = null;
		try {
			searchResults = service.searchArtist(artists, searchInput);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(searchResults.isEmpty());
		assertEquals("Artists must be provided", error);
	}

	@Test
	public void testSearchArtistsSearchNull() {
		Artist artist1 = new Artist();
		artist1.setUsername("Jonathan");
		Artist artist2 = new Artist();
		artist2.setUsername("Jonas");
		Artist artist3 = new Artist();
		artist3.setUsername("Juan");
		ArrayList<Artist> artists = new ArrayList<>();
		artists.add(artist1);
		artists.add(artist2);
		artists.add(artist3);
		String searchInput = null;
		String error = null;
		HashSet<Artist> searchResults = new HashSet<Artist>();
		try {
			searchResults = service.searchArtist(artists, searchInput);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(searchResults.isEmpty());
		assertEquals("Search cannot be empty", error);
	}

	@Test
	public void testSearchArtistsSearchEmpty() {
		Artist artist1 = new Artist();
		artist1.setUsername("Jonathan");
		Artist artist2 = new Artist();
		artist2.setUsername("Jonas");
		Artist artist3 = new Artist();
		artist3.setUsername("Juan");
		ArrayList<Artist> artists = new ArrayList<>();
		artists.add(artist1);
		artists.add(artist2);
		artists.add(artist3);
		String searchInput = "";
		String error = null;
		HashSet<Artist> searchResults = new HashSet<Artist>();
		try {
			searchResults = service.searchArtist(artists, searchInput);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(searchResults.isEmpty());
		assertEquals("Search cannot be empty", error);
	}

	@Test
	public void testSearchArtistsSearchSpaces() {
		Artist artist1 = new Artist();
		artist1.setUsername("Jonathan");
		Artist artist2 = new Artist();
		artist2.setUsername("Jonas");
		Artist artist3 = new Artist();
		artist3.setUsername("Juan");
		ArrayList<Artist> artists = new ArrayList<>();
		artists.add(artist1);
		artists.add(artist2);
		artists.add(artist3);
		String searchInput = " ";
		String error = null;
		HashSet<Artist> searchResults = new HashSet<Artist>();
		try {
			searchResults = service.searchArtist(artists, searchInput);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(searchResults.isEmpty());
		assertEquals("Search cannot be empty", error);
	}

	/*
	 * ========================================================= SEARCHARTWORK TESTS
	 * =========================================================
	 */

	@Test
	public void testSearchArtworkAllFilters() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setStyle(ArtStyle.IMPRESSIONIST);
		artwork1.setPrice(40.51);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setStyle(ArtStyle.IMPRESSIONIST);
		artwork2.setPrice(30.42);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setStyle(ArtStyle.IMPRESSIONIST);
		artwork3.setPrice(20.53);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setStyle(ArtStyle.IMPRESSIONIST);
		artwork4.setPrice(10000.424);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setStyle(ArtStyle.SURREALIST);
		artwork5.setPrice(25.66);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setStyle(ArtStyle.IMPRESSIONIST);
		artwork6.setPrice(27.23);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		try {
			results = service.searchArtwork(listings, "ther", 20.4, 40.6, ArtStyle.IMPRESSIONIST);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(results.contains(listing1));
		assertTrue(results.contains(listing2));
		assertFalse(results.contains(listing3));
		assertFalse(results.contains(listing4));
		assertFalse(results.contains(listing5));
		assertFalse(results.contains(listing6));
		assertEquals(results.size(), 2);
	}

	@Test
	public void testSearchArtworkAllFiltersListingsNull() {
		ArrayList<Listing> listings = null;

		HashSet<Listing> results = new HashSet<Listing>();
		String error = null;
		try {
			results = service.searchArtwork(listings, "ther", 20.4, 40.6, ArtStyle.IMPRESSIONIST);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Listings must be provided", error);
		assertEquals(results.size(), 0);
	}

	@Test
	public void testSearchArtworkAllFiltersSearchNull() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setStyle(ArtStyle.IMPRESSIONIST);
		artwork1.setPrice(40.51);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setStyle(ArtStyle.IMPRESSIONIST);
		artwork2.setPrice(30.42);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setStyle(ArtStyle.IMPRESSIONIST);
		artwork3.setPrice(20.53);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setStyle(ArtStyle.IMPRESSIONIST);
		artwork4.setPrice(10000.424);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setStyle(ArtStyle.SURREALIST);
		artwork5.setPrice(25.66);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setStyle(ArtStyle.IMPRESSIONIST);
		artwork6.setPrice(27.23);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		String error = null;
		try {
			results = service.searchArtwork(listings, null, 20.4, 40.6, ArtStyle.IMPRESSIONIST);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Search cannot be empty", error);
		assertEquals(results.size(), 0);
	}

	@Test
	public void testSearchArtworkAllFiltersSearchEmpty() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setStyle(ArtStyle.IMPRESSIONIST);
		artwork1.setPrice(40.51);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setStyle(ArtStyle.IMPRESSIONIST);
		artwork2.setPrice(30.42);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setStyle(ArtStyle.IMPRESSIONIST);
		artwork3.setPrice(20.53);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setStyle(ArtStyle.IMPRESSIONIST);
		artwork4.setPrice(10000.424);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setStyle(ArtStyle.SURREALIST);
		artwork5.setPrice(25.66);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setStyle(ArtStyle.IMPRESSIONIST);
		artwork6.setPrice(27.23);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		String error = null;
		try {
			results = service.searchArtwork(listings, "", 20.4, 40.6, ArtStyle.IMPRESSIONIST);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Search cannot be empty", error);
		assertEquals(results.size(), 0);
	}

	@Test
	public void testSearchArtworkAllFiltersSearchSpaces() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setStyle(ArtStyle.IMPRESSIONIST);
		artwork1.setPrice(40.51);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setStyle(ArtStyle.IMPRESSIONIST);
		artwork2.setPrice(30.42);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setStyle(ArtStyle.IMPRESSIONIST);
		artwork3.setPrice(20.53);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setStyle(ArtStyle.IMPRESSIONIST);
		artwork4.setPrice(10000.424);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setStyle(ArtStyle.SURREALIST);
		artwork5.setPrice(25.66);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setStyle(ArtStyle.IMPRESSIONIST);
		artwork6.setPrice(27.23);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		String error = null;
		try {
			results = service.searchArtwork(listings, "  ", 20.4, 40.6, ArtStyle.IMPRESSIONIST);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Search cannot be empty", error);
		assertEquals(results.size(), 0);
	}

	@Test
	public void testSearchArtworkAllFiltersMinLargerThanMax() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setStyle(ArtStyle.IMPRESSIONIST);
		artwork1.setPrice(40.51);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setStyle(ArtStyle.IMPRESSIONIST);
		artwork2.setPrice(30.42);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setStyle(ArtStyle.IMPRESSIONIST);
		artwork3.setPrice(20.53);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setStyle(ArtStyle.IMPRESSIONIST);
		artwork4.setPrice(10000.424);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setStyle(ArtStyle.SURREALIST);
		artwork5.setPrice(25.66);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setStyle(ArtStyle.IMPRESSIONIST);
		artwork6.setPrice(27.23);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		String error = null;
		try {
			results = service.searchArtwork(listings, "ther", 40.4, 20.6, ArtStyle.IMPRESSIONIST);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("Min price cannot be larger than max price", error);
		assertEquals(results.size(), 0);
	}

	@Test
	public void testSearchArtworkAllFiltersArtStyleNull() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setStyle(ArtStyle.IMPRESSIONIST);
		artwork1.setPrice(40.51);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setStyle(ArtStyle.IMPRESSIONIST);
		artwork2.setPrice(30.42);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setStyle(ArtStyle.IMPRESSIONIST);
		artwork3.setPrice(20.53);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setStyle(ArtStyle.IMPRESSIONIST);
		artwork4.setPrice(10000.424);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setStyle(ArtStyle.SURREALIST);
		artwork5.setPrice(25.66);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setStyle(ArtStyle.IMPRESSIONIST);
		artwork6.setPrice(27.23);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		String error = null;
		try {
			results = service.searchArtwork(listings, "ther", 20.4, 40.6, null);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("ArtStyle cannot be null", error);
		assertEquals(results.size(), 0);
	}

	@Test
	public void testSearchArtworkPriceFilter() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setPrice(40.51);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setPrice(30.42);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setPrice(20.53);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setPrice(10000.424);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setPrice(25.66);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setPrice(27.23);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		try {
			results = service.searchArtwork(listings, "ther", 20.4, 40.6);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(results.contains(listing1));
		assertTrue(results.contains(listing2));
		assertFalse(results.contains(listing3));
		assertFalse(results.contains(listing4));
		assertTrue(results.contains(listing5));
		assertFalse(results.contains(listing6));
		assertEquals(results.size(), 3);
	}

	@Test
	public void testSearchArtworkArtStyleFilter() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");
		artwork1.setStyle(ArtStyle.IMPRESSIONIST);

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");
		artwork2.setStyle(ArtStyle.IMPRESSIONIST);

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");
		artwork3.setStyle(ArtStyle.IMPRESSIONIST);

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");
		artwork4.setStyle(ArtStyle.IMPRESSIONIST);

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");
		artwork5.setStyle(ArtStyle.SURREALIST);

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");
		artwork6.setStyle(ArtStyle.IMPRESSIONIST);

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		try {
			results = service.searchArtwork(listings, "ther", ArtStyle.IMPRESSIONIST);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(results.contains(listing1));
		assertTrue(results.contains(listing2));
		assertFalse(results.contains(listing3));
		assertTrue(results.contains(listing4));
		assertFalse(results.contains(listing5));
		assertFalse(results.contains(listing6));
		assertEquals(results.size(), 3);
	}

	@Test
	public void testSearchArtworkNoFilter() {
		Artwork artwork1 = new Artwork();
		artwork1.setName("Father");

		Artwork artwork2 = new Artwork();
		artwork2.setName("Mother");

		Artwork artwork3 = new Artwork();
		artwork3.setName("Therapist");

		Artwork artwork4 = new Artwork();
		artwork4.setName("There");

		Artwork artwork5 = new Artwork();
		artwork5.setName("Therefore");

		Artwork artwork6 = new Artwork();
		artwork6.setName("Bothr");

		Listing listing1 = new Listing();
		listing1.setArtwork(artwork1);
		listing1.setIsSold(false);

		Listing listing2 = new Listing();
		listing2.setArtwork(artwork2);
		listing2.setIsSold(false);

		Listing listing3 = new Listing();
		listing3.setArtwork(artwork3);
		listing3.setIsSold(true);

		Listing listing4 = new Listing();
		listing4.setArtwork(artwork4);
		listing4.setIsSold(false);

		Listing listing5 = new Listing();
		listing5.setArtwork(artwork5);
		listing5.setIsSold(false);

		Listing listing6 = new Listing();
		listing6.setArtwork(artwork6);
		listing6.setIsSold(false);

		ArrayList<Listing> listings = new ArrayList<Listing>();
		listings.add(listing1);
		listings.add(listing2);
		listings.add(listing3);
		listings.add(listing4);
		listings.add(listing5);

		HashSet<Listing> results = new HashSet<Listing>();
		try {
			results = service.searchArtwork(listings, "ther");
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(results.contains(listing1));
		assertTrue(results.contains(listing2));
		assertFalse(results.contains(listing3));
		assertTrue(results.contains(listing4));
		assertTrue(results.contains(listing5));
		assertFalse(results.contains(listing6));
		assertEquals(results.size(), 4);
	}

	/*
	 * ========================================================= BROWSEHISTORY TESTS
	 * =========================================================
	 */

	@Test
	public void testAddToBrowseHistory() {
		Customer customer = new Customer();
		Artwork artwork = new Artwork();
		try {
			service.addToBrowseHistory(customer, artwork);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertTrue(service.viewBrowsingHistory(customer).contains(artwork));
	}

	@Test
	public void testAddToBrowseHistoryAlreadyViewed() {
		Customer customer = new Customer();
		Artwork artwork = new Artwork();
		try {
			service.addToBrowseHistory(customer, artwork);
			service.addToBrowseHistory(customer, artwork);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertTrue(service.viewBrowsingHistory(customer).contains(artwork));
		assertEquals(service.viewBrowsingHistory(customer).size(), 1);
	}

	@Test
	public void testAddToBrowseHistoryCustomerNull() {
		Customer customer = null;
		Artwork artwork = new Artwork();
		String error = null;
		try {
			service.addToBrowseHistory(customer, artwork);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Customer doesn't exist", error);
	}

	@Test
	public void testAddToBrowseHistoryArtworkNull() {
		Customer customer = new Customer();
		Artwork artwork = null;
		String error = null;
		try {
			service.addToBrowseHistory(customer, artwork);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertTrue(service.viewBrowsingHistory(customer).isEmpty());
		assertEquals("Artwork doesn't exist", error);
	}

	@Test
	public void testViewBrowsingHistory() {
		Customer customer = new Customer();
		Artwork artwork1 = new Artwork();
		Artwork artwork2 = new Artwork();
		service.addToBrowseHistory(customer, artwork1);
		service.addToBrowseHistory(customer, artwork2);
		HashSet<Artwork> browsingHistory = new HashSet<Artwork>();
		try {
			browsingHistory = (HashSet<Artwork>) service.viewBrowsingHistory(customer);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertTrue(browsingHistory.contains(artwork1));
		assertTrue(browsingHistory.contains(artwork2));
	}

	@Test
	public void testViewBrowsingHistoryEmpty() {
		Customer customer = new Customer();
		HashSet<Artwork> browsingHistory = new HashSet<Artwork>();
		try {
			browsingHistory = (HashSet<Artwork>) service.viewBrowsingHistory(customer);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals(browsingHistory.size(), 0);
	}

	@Test
	public void testViewBrowsingHistoryCustomerNull() {
		Customer customer = null;
		String error = null;
		try {
			service.viewBrowsingHistory(customer);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertEquals("Customer doesn't exist", error);
	}

}
