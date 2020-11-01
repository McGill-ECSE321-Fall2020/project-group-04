package ca.mcgill.ecse321.smartgallery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.util.ArrayList;
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
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private ListingRepository listingRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private ArtistRepository artistRepository;

	@Mock
	private SmartGalleryRepository smartGalleryRepository;

	@Mock
	private GalleryRepository galleryRepository;

	@Mock
	private ArtworkRepository artworkRepository;

	@InjectMocks
	private RegistrationService registrationService;

	// Customer Variables
	private static final String CUSTOMER_USERNAME = "testcustomer";
	private static final String UNIQUE_USER = "uniqueuser";
	private static final String UNIQUE_EMAIL = "uniqueuser@email.com";

	private static final String CUSTOMER_EMAIL = "testcustomer@email.com";
	private static final String CUSTOMER_PASSWORD = "testcpass";
	private static final Date CUSTOMER_CREATION_DATE = Date.valueOf("2020-09-01");
	private static final PaymentMethod DEFAULTPAY = PaymentMethod.CREDIT;

	private static final String CUSTOMER_USERNAME2 = "testcustomer2";
	private static final String CUSTOMER_EMAIL2 = "testcustomer2@email.com";
	private static final String CUSTOMER_PASSWORD2 = "testcpass2";
	private static final Date CUSTOMER_CREATION_DATE2 = Date.valueOf("2020-07-09");

	// Artist Variables
	private static final String ARTIST_USERNAME = "testartist";
	private static final String ARTIST_EMAIL = "testartist@email.com";
	private static final String ARTIST_PASSWORD = "testapass";
	private static final boolean VERIFIED = true;
	private static final Date ARTIST_CREATION_DATE = Date.valueOf("2020-09-10");
	
	private static final String ARTIST_USERNAME2 = "testartist42";
	private static final String ARTIST_EMAIL2 = "testartist333@email.com";
	private static final String ARTIST_PASSWORD2 = "testapassqwerq";

	// Gallery
	private static final String G_ID = "gallery";

	// SmartGallery variables
	private static final int SG_ID = 1234;

	private static Artist ARTIST1;
	private static Customer CUSTOMER1;

	@BeforeEach
	public void setMockOutput() {
		MockitoAnnotations.initMocks(this);
		lenient().when(smartGalleryRepository.findSmartGalleryBySmartGalleryID(anyInt()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(SG_ID)) {
						SmartGallery smartGallery = new SmartGallery();
						smartGallery.setSmartGalleryID(SG_ID);
						return smartGallery;
					} else {
						return null;
					}
				});

		// TODO PORT
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
					} else if (invocation.getArgument(0).equals(CUSTOMER_USERNAME2)) {
						Customer customer = new Customer();
						customer.setUsername(CUSTOMER_USERNAME2);
						customer.setPassword(CUSTOMER_PASSWORD2);
						customer.setEmail(CUSTOMER_EMAIL2);
						customer.setCreationDate(CUSTOMER_CREATION_DATE2);
						customer.setDefaultPaymentMethod(DEFAULTPAY);
						customer.setLoggedIn(true);
						return customer;
					} else {
						return null;
					}
				});

		lenient().when(customerRepository.findCustomerByEmail(anyString()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(CUSTOMER_EMAIL)) {
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

		lenient().when(customerRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Customer> customers = new ArrayList<>();
			Customer customer = new Customer();
			customer.setUsername(UNIQUE_USER);
			customers.add(customer);
			return customers;
		});

		// TODO PORT
		lenient().when(artistRepository.findArtistByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARTIST_USERNAME)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME);
				artist.setPassword(ARTIST_PASSWORD);
				artist.setEmail(ARTIST_EMAIL);
				artist.setCreationDate(ARTIST_CREATION_DATE);
				artist.setDefaultPaymentMethod(DEFAULTPAY);
				artist.setIsVerified(VERIFIED);
				return artist;
			}else if (invocation.getArgument(0).equals(ARTIST_USERNAME2)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME2);
				artist.setPassword(ARTIST_PASSWORD2);
				artist.setEmail(ARTIST_EMAIL2);
				artist.setCreationDate(ARTIST_CREATION_DATE);
				artist.setDefaultPaymentMethod(DEFAULTPAY);
				artist.setIsVerified(VERIFIED);
				artist.setLoggedIn(true);
				return artist;
			} else {
				return null;
			}
		});

		lenient().when(artistRepository.findArtistByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ARTIST_EMAIL)) {
				Artist artist = new Artist();
				artist.setUsername(ARTIST_USERNAME);
				artist.setPassword(ARTIST_PASSWORD);
				artist.setEmail(ARTIST_EMAIL);
				artist.setCreationDate(ARTIST_CREATION_DATE);
				artist.setDefaultPaymentMethod(DEFAULTPAY);
				artist.setIsVerified(VERIFIED);
				return artist;
			} else {
				return null;
			}
		});

		lenient().when(artistRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Artist> artists = new ArrayList<>();
			Artist artist = new Artist();
			artist.setUsername(UNIQUE_USER);
			artists.add(artist);
			return artists;
		});

		lenient().when(artistRepository.findArtistByIsVerified(anyBoolean()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(true)) {
						List<Artist> artists = new ArrayList<>();
						Artist artist = new Artist();
						artist.setIsVerified(VERIFIED);
						artist.setUsername(UNIQUE_USER);
						artists.add(artist);
						return artists;
					} else {
						List<Artist> artists = new ArrayList<>();
						Artist artist = new Artist();
						artist.setIsVerified(!VERIFIED);
						artist.setUsername(UNIQUE_USER);
						artists.add(artist);
						return artists;
					}
				});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);

		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(artistRepository.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(transactionRepository.save(any(Transaction.class))).thenAnswer(returnParameterAsAnswer);
		lenient().doNothing().when(customerRepository).delete(any(Customer.class));
		lenient().doNothing().when(artistRepository).delete(any(Artist.class));
	}

	@Test
	public void testProfileLoginAndLogout() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		String correctPassword = customer.getPassword();
		String wrongPassword = "wrongPassword";

		assertFalse(customer.isLoggedIn());
		registrationService.login(customer, correctPassword);
		assertTrue(customer.isLoggedIn());

		registrationService.logout(customer);
		assertFalse(customer.isLoggedIn());

		registrationService.login(customer, wrongPassword);
		assertFalse(customer.isLoggedIn());

		correctPassword = artist.getPassword();

		assertFalse(artist.isLoggedIn());
		registrationService.login(artist, correctPassword);
		assertTrue(artist.isLoggedIn());

		registrationService.logout(artist);
		assertFalse(artist.isLoggedIn());

		registrationService.login(artist, wrongPassword);
		assertFalse(artist.isLoggedIn());
	}

	@Test
	public void testPasswordChange() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		String oldPassword = customer.getPassword();
		String newPassword = "newpassword";
		String wrongOldPassword = "wrongpassword";

		assertNotEquals(customer.getPassword(), newPassword);
		registrationService.updatePassword(customer, wrongOldPassword, newPassword);
		assertNotEquals(customer.getPassword(), newPassword);
		registrationService.updatePassword(customer, oldPassword, newPassword);
		assertEquals(customer.getPassword(), newPassword);

		oldPassword = artist.getPassword();

		assertNotEquals(artist.getPassword(), newPassword);
		registrationService.updatePassword(artist, wrongOldPassword, newPassword);
		assertNotEquals(artist.getPassword(), newPassword);
		registrationService.updatePassword(artist, oldPassword, newPassword);
		assertEquals(artist.getPassword(), newPassword);
	}

	@Test
	public void testEmailChange() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		String email = "newEmail@email.com";
		String password = customer.getPassword();
		String wrongPassword = "wrongpassword";

		assertNotEquals(customer.getEmail(), email);
		registrationService.updateEmail(customer, email, wrongPassword);
		assertNotEquals(customer.getEmail(), email);
		registrationService.updateEmail(customer, email, password);
		assertEquals(customer.getEmail(), email);

		password = artist.getPassword();

		assertNotEquals(artist.getEmail(), email);
		registrationService.updateEmail(artist, email, wrongPassword);
		assertNotEquals(artist.getEmail(), email);
		registrationService.updateEmail(artist, email, password);
		assertEquals(artist.getEmail(), email);
	}

	/*
	 * =========================== Customer ===========================
	 */

	@Test
	public void testCreateCustomer() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Customer customer = null;
		try {
			customer = registrationService.createCustomer(UNIQUE_USER, CUSTOMER_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY,
					sGallery);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		assertNotNull(customer);
		assertEquals(customer.getUsername(), UNIQUE_USER);
	}

	@Test
	public void testCreateCustomerNoUsername() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(null, CUSTOMER_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Non empty username must be provided", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerEmptyUsername() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer("", CUSTOMER_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Non empty username must be provided", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerNoPass() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(UNIQUE_USER, null, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Password with 7 or more characters is required", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerShortPass() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(UNIQUE_USER, "", UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Password with 7 or more characters is required", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerNoEmail() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(UNIQUE_USER, CUSTOMER_PASSWORD, null, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerEmptyEmail() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(UNIQUE_USER, CUSTOMER_PASSWORD, "", DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerInvalidEmail() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(UNIQUE_USER, CUSTOMER_PASSWORD, "asldkfj", DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerInvalidPayment() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(UNIQUE_USER, CUSTOMER_PASSWORD, UNIQUE_EMAIL, null, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Default payment method must be set to 'credit' or 'paypal'", e.getMessage());
		}
	}

	@Test
	public void testGetCustomerByName() {
		Customer c2 = registrationService.getCustomer(CUSTOMER_USERNAME);
		assertEquals(CUSTOMER_USERNAME, c2.getUsername());
	}

	@Test
	public void testGetCustomerByEmptyName() {
		try {
			registrationService.getCustomer("");
		} catch (IllegalArgumentException e) {
			assertEquals("Username is empty", e.getMessage());
		}
	}

	@Test
	public void testGetCustomerByNullName() {
		try {
			registrationService.getCustomer(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Username is empty", e.getMessage());
		}
	}

	@Test
	public void testGetCustomerByInvalidName() {
		try {
			registrationService.getCustomer("wronguser");
		} catch (IllegalArgumentException e) {
			assertEquals("Customer doesn't exist", e.getMessage());
		}
	}

	@Test
	public void testGetCustomerByEmail() {
		Customer c2 = registrationService.getCustomerByEmail(CUSTOMER_EMAIL);
		assertEquals(CUSTOMER_EMAIL, c2.getEmail());
	}

	@Test
	public void testGetCustomerByEmptyEmail() {
		try {
			registrationService.getCustomerByEmail("");
		} catch (IllegalArgumentException e) {
			assertEquals("Empty email was provided", e.getMessage());
		}
	}

	@Test
	public void testGetCustomerByNullEmail() {
		try {
			registrationService.getCustomerByEmail(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Empty email was provided", e.getMessage());
		}
	}

	@Test
	public void testGetCustomerByInvalidEmail() {
		try {
			registrationService.getCustomerByEmail("abc123");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}

	}

	@Test
	public void testGetCustomerByNonRegisteredEmail() {
		try {
			registrationService.getCustomerByEmail("abc123@gmail.com");
		} catch (IllegalArgumentException e) {
			assertEquals("Customer with this email doesn't exist", e.getMessage());
		}

	}

	@Test
	public void testGetAllCustomers() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Customer customer = null;
		try {
			customer = registrationService.createCustomer(UNIQUE_USER, CUSTOMER_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY,
					sGallery);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Customer> customers = new ArrayList<>();
		customers = registrationService.getAllCustomers();
		assertEquals(customer.getUsername(), customers.get(0).getUsername());

	}

	@Test
	public void testDeleteCustomer() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME2);
		customer.setLoggedIn(true);
		Customer c2 = null;
		try {
			c2 = registrationService.deleteCustomer(CUSTOMER_USERNAME2);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		assertNotNull(c2);
		assertEquals(customer.getUsername(), c2.getUsername());
	}

	@Test
	public void testDeleteCustomerNoLogin() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		customer.setLoggedIn(false);
		try {
			registrationService.deleteCustomer(CUSTOMER_USERNAME);
		} catch (IllegalArgumentException e) {
			assertEquals("Customer must be logged in", e.getMessage());
		}
	}

	@Test
	public void testDeleteNonExistingCustomer() {
		try {
			registrationService.deleteCustomer("invalidUser");
		} catch (IllegalArgumentException e) {
			assertEquals("Customer doesn't exist", e.getMessage());
		}
	}

	@Test
	public void testDeleteCustomerWrongUsername() {
		try {
			registrationService.deleteCustomer("");
		} catch (IllegalArgumentException e) {
			assertEquals("Empty username was provided", e.getMessage());
		}
	}

	/*
	 * =========================== Artist ===========================
	 */

	@Test
	public void testCreateArtist() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Artist artist = null;
		try {
			artist = registrationService.createArtist(UNIQUE_USER, UNIQUE_EMAIL, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		assertNotNull(artist);
		assertEquals(artist.getUsername(), UNIQUE_USER);
	}

	@Test
	public void testCreateArtistNoUsername() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist(null, ARTIST_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Non empty username must be provided", e.getMessage());
		}
	}

	@Test
	public void testCreateArtistEmptyUsername() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist("", ARTIST_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Non empty username must be provided", e.getMessage());
		}
	}

	@Test
	public void testCreateArtistNoPass() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist(UNIQUE_USER, null, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Password with 7 or more characters is required", e.getMessage());
		}
	}

	@Test
	public void testCreateArtistShortPass() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist(UNIQUE_USER, "", UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Password with 7 or more characters is required", e.getMessage());
		}
	}

	@Test
	public void testCreateArtistNoEmail() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist(UNIQUE_USER, ARTIST_PASSWORD, null, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}
	}

	@Test
	public void testCreateArtistEmptyEmail() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist(UNIQUE_USER, ARTIST_PASSWORD, "", DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}
	}

	@Test
	public void testCreateArtistInvalidEmail() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist(UNIQUE_USER, ARTIST_PASSWORD, "asldkfj", DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}
	}

	@Test
	public void testCreateArtistInvalidPayment() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createArtist(UNIQUE_USER, ARTIST_PASSWORD, UNIQUE_EMAIL, null, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Default payment method must be set to 'credit' or 'paypal'", e.getMessage());
		}
	}

	@Test
	public void testGetArtistByName() {
		Artist c2 = registrationService.getArtist(ARTIST_USERNAME);
		assertEquals(ARTIST_USERNAME, c2.getUsername());
	}

	@Test
	public void testGetArtistByEmptyName() {
		try {
			registrationService.getArtist("");
		} catch (IllegalArgumentException e) {
			assertEquals("Username is empty", e.getMessage());
		}
	}

	@Test
	public void testGetArtistByNullName() {
		try {
			registrationService.getArtist(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Username is empty", e.getMessage());
		}
	}

	@Test
	public void testGetArtistByInvalidName() {
		try {
			registrationService.getArtist("wronguser");
		} catch (IllegalArgumentException e) {
			assertEquals("Artist doesn't exist", e.getMessage());
		}
	}

	@Test
	public void testGetArtistByEmail() {
		Artist c2 = registrationService.getArtistByEmail(ARTIST_EMAIL);
		assertEquals(ARTIST_EMAIL, c2.getEmail());
	}

	@Test
	public void testGetArtistByEmptyEmail() {
		try {
			registrationService.getArtistByEmail("");
		} catch (IllegalArgumentException e) {
			assertEquals("Empty email was provided", e.getMessage());
		}
	}

	@Test
	public void testGetArtistByNullEmail() {
		try {
			registrationService.getArtistByEmail(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Empty email was provided", e.getMessage());
		}
	}

	@Test
	public void testGetArtistByInvalidEmail() {
		try {
			registrationService.getArtistByEmail("abc123");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}

	}

	@Test
	public void testGetArtistByNonRegisteredEmail() {
		try {
			registrationService.getArtistByEmail("abc123@gmail.com");
		} catch (IllegalArgumentException e) {
			assertEquals("Artist with this email doesn't exist", e.getMessage());
		}

	}

	@Test
	public void testGetAllArtists() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Artist artist = null;
		try {
			artist = registrationService.createArtist(UNIQUE_USER, ARTIST_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Artist> artists = new ArrayList<>();
		artists = registrationService.getAllArtists();
		assertEquals(artist.getUsername(), artists.get(0).getUsername());

	}

	@Test
	public void testVerifyAndUnverifyArtist() {

		Artist artist = registrationService.getArtist(ARTIST_USERNAME);

		assertTrue(artist.isVerified());
		registrationService.verifyArtist(artist);
		assertTrue(artist.isVerified());

		registrationService.unverifyArtist(artist);
		assertFalse(artist.isVerified());

		registrationService.unverifyArtist(artist);
		assertFalse(artist.isVerified());

		registrationService.verifyArtist(artist);
		assertTrue(artist.isVerified());
	}

	@Test
	public void testGetAllVerifiedArtists() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Artist artist = null;
		try {
			artist = registrationService.createArtist(UNIQUE_USER, ARTIST_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
			registrationService.verifyArtist(artist);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Artist> artists = new ArrayList<>();
		artists = registrationService.getAllVerifiedArtists();
		assertEquals(artist.getUsername(), artists.get(0).getUsername());
		assertTrue(artists.get(0).isVerified());
	}

	@Test
	public void testGetAllNonVerifiedArtists() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Artist artist = null;
		try {
			artist = registrationService.createArtist(UNIQUE_USER, ARTIST_PASSWORD, UNIQUE_EMAIL, DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Artist> artists = new ArrayList<>();
		artists = registrationService.getAllNonVerifiedArtists();
		assertEquals(artist.getUsername(), artists.get(0).getUsername());
		assertFalse(artists.get(0).isVerified());
	}

	@Test
	public void testDeleteArtist() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME2);
		Artist a = null;
		try {
			a = registrationService.deleteArtist(ARTIST_USERNAME2);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		assertNotNull(a);
		assertEquals(artist.getUsername(), a.getUsername());
	}

	@Test
	public void testDeleteArtistNoLogin() {
		Artist artist = artistRepository.findArtistByUsername(ARTIST_USERNAME);
		try {
			registrationService.deleteArtist(ARTIST_USERNAME);
		} catch (IllegalArgumentException e) {
			assertEquals("Artist must be logged in", e.getMessage());
		}
	}

	@Test
	public void testDeleteNonExistingArtist() {
		try {
			registrationService.deleteArtist("invaliduser");
		} catch (IllegalArgumentException e) {
			assertEquals("Artist doesn't exist", e.getMessage());
		}
	}

	@Test
	public void testDeleteWrongUsername() {
		try {
			registrationService.deleteArtist("");
		} catch (IllegalArgumentException e) {
			assertEquals("Empty username was provided", e.getMessage());
		}
	}
}
