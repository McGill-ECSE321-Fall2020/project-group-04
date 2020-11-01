package ca.mcgill.ecse321.smartgallery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.Listing;
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
	
	// Gallery
	private static final String G_ID = "gallery";

	// SmartGallery variables
	private static final int SG_ID = 1234;
	
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
					}else if(invocation.getArgument(0).equals(CUSTOMER_USERNAME2)) {
						Customer customer = new Customer();
						customer.setUsername(CUSTOMER_USERNAME2);
						customer.setPassword(CUSTOMER_PASSWORD2);
						customer.setEmail(CUSTOMER_EMAIL2);
						customer.setCreationDate(CUSTOMER_CREATION_DATE2);
						customer.setDefaultPaymentMethod(DEFAULTPAY);
						return customer;
					} else {
						return null;
					}
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
			} else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		lenient().when(transactionRepository.save(any(Transaction.class))).thenAnswer(returnParameterAsAnswer);

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
	
}