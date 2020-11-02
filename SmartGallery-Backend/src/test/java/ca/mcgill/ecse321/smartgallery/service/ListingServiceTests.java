package ca.mcgill.ecse321.smartgallery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class ListingServiceTests {

	@Mock
	private ListingRepository listingRepository;

	@Mock
	private ArtistRepository artistRepository;

	@Mock
	private SmartGalleryRepository smartGalleryRepository;

	@Mock
	private GalleryRepository galleryRepository;

	@Mock
	private ArtworkRepository artworkRepository;

	@InjectMocks
	private ListingService listingService;

	// Gallery
	private static final String G_ID = "gallery";

	// Artist Variables
	private static final String ARTIST_USERNAME = "testartist";
	private static final String ARTIST_EMAIL = "testartist@email.com";
	private static final String ARTIST_PASSWORD = "testapass";
	private static final boolean VERIFIED = true;
	private static final Date ARTIST_CREATION_DATE = Date.valueOf("2020-09-10");
	private static final String ARTIST_USERNAME2 = "testartist2";
	private static final String ARTIST_EMAIL2 = "testartist2@email.com";
	private static final String ARTIST_PASSWORD2 = "testapass2";

	// Artwork variables
	private static String ARTWORK_NAME = "artwork";
	private static final Integer YEAR = 2019;
	private static final Double PRICE = 200.00;
	private static final boolean PROMOTED = true;
	private static final ArtStyle STYLE = ArtStyle.IMPRESSIONIST;
	private static final Integer HEIGHT = 300;
	private static final Integer WEIGHT = 25;
	private static final Integer WIDTH = 50;
	private static final int A_ID = 393939;

	// Listing variables
	private static final boolean NOT_SOLD = false;
	private static final Date DATE_LISTED = Date.valueOf("2020-11-25");
	private static final int L_ID = 321;

	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);

		// Create Gallery
		lenient().when(galleryRepository.findGalleryByGalleryName(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(G_ID)) {
						Gallery gallery = new Gallery();
						gallery.setGalleryName(G_ID);
						return gallery;
					} else {
						return null;
					}
				});

		// Create Artist
		lenient().when(artistRepository.findArtistByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARTIST_USERNAME)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME);
				artist.setPassword(ARTIST_PASSWORD);
				artist.setEmail(ARTIST_EMAIL);
				artist.setCreationDate(ARTIST_CREATION_DATE);
				artist.setIsVerified(VERIFIED);
				return artist;
			}

			else if (invocation.getArgument(0).equals(ARTIST_USERNAME2)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME2);
				artist.setPassword(ARTIST_PASSWORD2);
				artist.setEmail(ARTIST_EMAIL2);
				artist.setCreationDate(ARTIST_CREATION_DATE);
				artist.setIsVerified(VERIFIED);
				Artwork artwork = new Artwork();
				artwork.setArtworkID(A_ID);
				artwork.setHeight(HEIGHT);
				artwork.setWeight(WEIGHT);
				artwork.setPrice(PRICE);
				artwork.setYear(YEAR);
				artwork.setIsBeingPromoted(PROMOTED);
				artwork.setStyle(STYLE);
				artwork.setName(ARTWORK_NAME);
				HashSet<Artist> artists = new HashSet();
				artists.add(artist);
				artwork.setArtists(artists);
				HashSet<Artwork> artworks = new HashSet();
				artworks.add(artwork);
				artist.setArtworks(artworks);
				return artist;
			} else {
				return null;
			}
		});

		// Create Listing
		lenient().when(listingRepository.findListingByListingID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(L_ID)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME);
				artist.setPassword(ARTIST_PASSWORD);
				artist.setEmail(ARTIST_EMAIL);
				artist.setCreationDate(ARTIST_CREATION_DATE);
				artist.setIsVerified(VERIFIED);
				Artwork artwork = new Artwork();
				artwork.setArtworkID(A_ID);
				artwork.setHeight(HEIGHT);
				artwork.setWeight(WEIGHT);
				artwork.setPrice(PRICE);
				artwork.setYear(YEAR);
				artwork.setIsBeingPromoted(PROMOTED);
				artwork.setStyle(STYLE);
				artwork.setName(ARTWORK_NAME);
				HashSet<Artist> artists = new HashSet();
				artists.add(artist);
				artwork.setArtists(artists);
				Listing listing = new Listing();
				listing.setIsSold(NOT_SOLD);
				listing.setListedDate(DATE_LISTED);
				listing.setListingID(L_ID);
				listing.setArtwork(artwork);
				HashSet<Artwork> artworkss = new HashSet<>();
				artworkss.add(artwork);
				artist.setArtworks(artworkss);
				return listing;
			} else {
				return null;
			}
		});

		// Create Artwork
		lenient().when(artworkRepository.findArtworkByArtworkID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(A_ID)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME);
				artist.setPassword(ARTIST_PASSWORD);
				artist.setEmail(ARTIST_EMAIL);
				artist.setCreationDate(ARTIST_CREATION_DATE);
				artist.setIsVerified(VERIFIED);
				Artwork artwork = new Artwork();
				artwork.setArtworkID(A_ID);
				artwork.setHeight(HEIGHT);
				artwork.setWeight(WEIGHT);
				artwork.setPrice(PRICE);
				artwork.setYear(YEAR);
				artwork.setIsBeingPromoted(PROMOTED);
				artwork.setStyle(STYLE);
				artwork.setName(ARTWORK_NAME);
				HashSet<Artist> artists = new HashSet();
				artists.add(artist);
				artwork.setArtists(artists);
				
				return artwork;
			} else {
				return null;
			}
		});

		// TODO set links?
		lenient().when(listingRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Listing> listings = new ArrayList<>();
			Listing listing = new Listing();
			listing.setListingID(L_ID);
			listings.add(listing);
			return listings;
		});
		
		// TODO set links?
				lenient().when(artworkRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
					List<Artwork> artworks = new ArrayList<>();
					Artwork artwork = new Artwork();
					artwork.setArtworkID(A_ID);
					artworks.add(artwork);
					return artworks;
				});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		lenient().when(listingRepository.save(any(Listing.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(artworkRepository.save(any(Artwork.class))).thenAnswer(returnParameterAsAnswer);

	}

	@Test
	public void testCreateArtwork() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
		}

		assertNotNull(artwork);
		assertEquals(artwork.getArtworkID(), artwork.getName().hashCode() * artist.getUsername().hashCode());
	}

	@Test
	public void testCreateArtworkArtistNull() {
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH,
					null, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Artist must exist", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkArtistNotExisting() {
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artist artist = artistRepository.findArtistByUsername("NotExisting");
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Artist must exist", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkGalleryNull() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH,
					artist, null);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Gallery must exist", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkGalleryNotExisting() {
		Gallery gallery = galleryRepository.findGalleryByGalleryName("GalleryNotExisting");
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Gallery must exist", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkNoName() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(null, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH, artist,
					gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Artwork name must be specified", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkNoYear() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, null, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Year must be specified", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkNoPrice() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, null, PROMOTED, STYLE, HEIGHT, WEIGHT, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Price must be specified", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkNoStyle() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, null, HEIGHT, WEIGHT, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Art style must be specified", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkNoHeight() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, null, WEIGHT, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Height must be specified", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkNoWeight() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, null, WIDTH,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Weight must be specified", e.getMessage());
		}
	}

	@Test
	public void testCreateArtworkNoWidth() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.createArtwork(ARTWORK_NAME, YEAR, PRICE, PROMOTED, STYLE, HEIGHT, WEIGHT, null,
					artist, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Width must be specified", e.getMessage());
		}
	}

	@Test
	public void testCreateListing() {

		Artwork artwork = artworkRepository.findArtworkByArtworkID(A_ID);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Listing listing = null;
		try {
			listing = listingService.createListing(artwork, DATE_LISTED, PRICE, gallery);

		} catch (IllegalArgumentException e) {

			// e.printStackTrace();
		}

		assertNotNull(listing);
		assertEquals(listing.getListingID(),
				artwork.getArtworkID() * artwork.getArtists().iterator().next().getUsername().hashCode());
	}

	@Test
	public void testCreateListingArtworkNull() {

		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Listing listing = null;
		try {
			listing = listingService.createListing(null, DATE_LISTED, PRICE, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Artwork must exist", e.getMessage());
		}

	}

	@Test
	public void testCreateListingArtworkNotExisting() {

		Artwork artwork = artworkRepository.findArtworkByArtworkID(494949);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Listing listing = null;
		try {
			listing = listingService.createListing(artwork, DATE_LISTED, PRICE, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Artwork must exist", e.getMessage());
		}

	}

	@Test
	public void testCreateListingGalleryNull() {

		Artwork artwork = artworkRepository.findArtworkByArtworkID(A_ID);
		Listing listing = null;
		try {
			listing = listingService.createListing(artwork, DATE_LISTED, PRICE, null);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Gallery must exist", e.getMessage());
		}

	}

	@Test
	public void testCreateListingGalleryNotExisting() {

		Artwork artwork = artworkRepository.findArtworkByArtworkID(A_ID);
		Gallery gallery = galleryRepository.findGalleryByGalleryName("galleryNotExisting");
		Listing listing = null;
		try {
			listing = listingService.createListing(artwork, DATE_LISTED, PRICE, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Gallery must exist", e.getMessage());
		}

	}

	@Test
	public void testCreateListingDateNull() {

		Artwork artwork = artworkRepository.findArtworkByArtworkID(A_ID);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Listing listing = null;
		try {
			listing = listingService.createListing(artwork, null, PRICE, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Listed date must be specified", e.getMessage());
		}

	}

	@Test
	public void testCreateListingPriceNull() {

		Artwork artwork = artworkRepository.findArtworkByArtworkID(A_ID);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(G_ID);
		Listing listing = null;
		try {
			listing = listingService.createListing(artwork, DATE_LISTED, null, gallery);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Price must be specified", e.getMessage());
		}

	}

	@Test
	public void testAddArtist() {

		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME2);

		Artwork updatedArtwork = null;

		try {
			updatedArtwork = listingService.addArtist(artist, A_ID);

		} catch (IllegalArgumentException e) {

		}

		assertNotNull(updatedArtwork);
		assertTrue(updatedArtwork.getArtists().contains(artist));
	}

	@Test
	public void testAddArtistArtistNull() {

		Artwork updatedArtwork = null;

		try {
			updatedArtwork = listingService.addArtist(null, A_ID);

		} catch (IllegalArgumentException e) {
			assertEquals("Artist must exist", e.getMessage());
		}

	}

	@Test
	public void testAddArtistArtistNotExisting() {

		Artist artist = artistRepository.findArtistByUsername("nonExistingArtist");
		Artwork updatedArtwork = null;

		try {
			updatedArtwork = listingService.addArtist(artist, A_ID);

		} catch (IllegalArgumentException e) {
			assertEquals("Artist must exist", e.getMessage());
		}

	}

	@Test
	public void testAddArtistArtworkNotExisting() {

		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME2);
		Artwork updatedArtwork = null;

		try {
			updatedArtwork = listingService.addArtist(artist, 555555);

		} catch (IllegalArgumentException e) {
			assertEquals("Artwork must exist", e.getMessage());
		}

	}

	@Test
	public void testUpdateArtworkListed() {

		Listing listing = listingRepository.findListingByListingID(L_ID);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, YEAR, PRICE, STYLE, HEIGHT, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		assertNotNull(artwork);
		assertEquals(name, artwork.getName());

	}

	@Test
	public void testUpdateArtworkListedNoName() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, null, YEAR, PRICE, STYLE, HEIGHT, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			assertEquals("Artwork name must be specified", e.getMessage());
		}

	}

	@Test
	public void testUpdateArtworkListedNoYear() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, null, PRICE, STYLE, HEIGHT, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Year must be specified", e.getMessage());
		}
	}

	@Test
	public void testUpdateArtworkListedNoPrice() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, YEAR, null, STYLE, HEIGHT, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Price must be specified", e.getMessage());
		}
	}

	@Test
	public void testUpdateArtworkListedNoStyle() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, YEAR, PRICE, null, HEIGHT, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			assertEquals("Art style must be specified", e.getMessage());
		}
	}

	@Test
	public void testUpdateArtworkListedNoHeight() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, YEAR, PRICE, STYLE, null, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			assertEquals("Height must be specified", e.getMessage());
		}

	}

	@Test
	public void testUpdateArtworkListedNoWidth() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, YEAR, PRICE, STYLE, HEIGHT, null, WEIGHT);
		} catch (IllegalArgumentException e) {
			assertEquals("Width must be specified", e.getMessage());
		}
	}

	@Test
	public void testUpdateArtworkListedNoWeight() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, YEAR, PRICE, STYLE, HEIGHT, WIDTH, null);
		} catch (IllegalArgumentException e) {
			assertEquals("Weight must be specified", e.getMessage());
		}
	}

	@Test
	public void testUpdateArtworkListingNull() {
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(null, name, YEAR, PRICE, STYLE, HEIGHT, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			assertEquals("Listing must exist", e.getMessage());
		}
	}

	@Test
	public void testUpdateArtworkListingNotExisting() {
		Listing listing = listingRepository.findListingByListingID(55555);
		String name = "newName";
		Artwork artwork = null;
		try {
			artwork = listingService.updateArtworkListed(listing, name, YEAR, PRICE, STYLE, HEIGHT, WIDTH, WEIGHT);
		} catch (IllegalArgumentException e) {
			assertEquals("Listing must exist", e.getMessage());
		}
	}

	@Test
	public void testDeleteListingAndArtwork() {

		Listing listing = listingRepository.findListingByListingID(L_ID);
		Artwork artwork = null;
		try {
			artwork = listingService.deleteListingAndArtwork(listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		assertNotNull(artwork);
		assertEquals(artwork.getListing(), null);

	}

	@Test
	public void testDeleteListingAndArtworkListingNull() {

		Artwork artwork = null;
		try {
			artwork = listingService.deleteListingAndArtwork(null);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Listing must exist", e.getMessage());
		}

	}

	@Test
	public void testDeleteListingAndArtworkListingNotExisting() {

		Listing listing = listingRepository.findListingByListingID(55555555);
		Artwork artwork = null;
		try {
			artwork = listingService.deleteListingAndArtwork(listing);
		} catch (IllegalArgumentException e) {
			// e.printStackTrace();
			assertEquals("Listing must exist", e.getMessage());
		}

	}

	@Test
	public void testGetListingByID() {

		Listing listing = listingRepository.findListingByListingID(L_ID);
		Listing listingToFind = null;

		try {
			listingToFind = listingService.getListingByID(L_ID);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		assertEquals(listing.getListingID(), listingToFind.getListingID());
	}

	@Test
	public void testGetArtworkByID() {

		Artwork artwork = artworkRepository.findArtworkByArtworkID(A_ID);
		Artwork artworkToFind = null;

		try {
			artworkToFind = listingService.getArtworkByID(A_ID);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		assertEquals(artwork.getArtworkID(), artworkToFind.getArtworkID());
	}

	@Test
	public void testGetAllListings() {
		Listing listing = null;

		try {
			listing = listingRepository.findListingByListingID(L_ID);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Listing> listingList = listingService.getAllListings();
		assertEquals(listing.getListingID(), listingList.get(0).getListingID());
	}
	
	@Test
	public void testGetAllArtworks() {
		Artwork artwork = null;

		try {
			artwork = artworkRepository.findArtworkByArtworkID(A_ID);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Artwork> artworkList = listingService.getAllArtworks();
		assertEquals(artwork.getArtworkID(), artworkList.get(0).getArtworkID());
	}

}
