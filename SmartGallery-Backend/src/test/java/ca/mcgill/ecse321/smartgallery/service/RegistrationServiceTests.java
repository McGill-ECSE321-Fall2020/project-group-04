package ca.mcgill.ecse321.smartgallery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {

	@Mock
	private SmartGalleryRepository smartGalleryRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private ArtistRepository artistRepository;

	@InjectMocks
	private RegistrationService registrationService;

	// Customer Variables
	private static final String CUSTOMER_USERNAME = "testcustomer";
	private static final String UNIQUE_USER = "uniqueuser";
	private static final String UNIQUE_EMAIL = "testcustomer4321@email.com";
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

	private static Artist ARTIST1;
	private static Customer CUSTOMER1;

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
			} else {
				return null;
			}
		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(artistRepository.save(any(Artist.class))).thenAnswer(returnParameterAsAnswer);
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
			assertEquals("Non empty email must be provided", e.getMessage());
		}
	}

	@Test
	public void testCreateCustomerEmptyEmail() {
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			registrationService.createCustomer(UNIQUE_USER, CUSTOMER_PASSWORD, "", DEFAULTPAY, sGallery);
		} catch (IllegalArgumentException e) {
			assertEquals("Non empty email must be provided", e.getMessage());
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
	public void getCustomerByName() {
		Customer c2 = registrationService.getCustomer(CUSTOMER_USERNAME);
		assertEquals(CUSTOMER_USERNAME, c2.getUsername());
	}

	@Test
	public void getCustomerByEmptyName() {
		try {
			registrationService.getCustomer("");
		} catch (IllegalArgumentException e) {
			assertEquals("Username is empty", e.getMessage());
		}
	}

	@Test
	public void getCustomerByNullName() {
		try {
			registrationService.getCustomer(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Username is empty", e.getMessage());
		}
	}

	@Test
	public void getCustomerByInvalidName() {
		try {
			registrationService.getCustomer("wronguser");
		} catch (IllegalArgumentException e) {
			assertEquals("Customer doesn't exist", e.getMessage());
		}
	}

	@Test
	public void getCustomerByEmail() {
		Customer c2 = registrationService.getCustomerByEmail(CUSTOMER_EMAIL);
		assertEquals(CUSTOMER_EMAIL, c2.getEmail());
	}

	@Test
	public void getCustomerByEmptyEmail() {
		try {
			registrationService.getCustomerByEmail("");
		} catch (IllegalArgumentException e) {
			assertEquals("Empty email was provided", e.getMessage());
		}
	}

	@Test
	public void getCustomerByNullEmail() {
		try {
			registrationService.getCustomerByEmail(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Empty email was provided", e.getMessage());
		}
	}

	@Test
	public void getCustomerByInvalidEmail() {
		try {
			registrationService.getCustomerByEmail("abc123");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid email format", e.getMessage());
		}

	}
	
	@Test
	public void getCustomerByNonRegisteredEmail() {
		try {
			registrationService.getCustomerByEmail("abc123@gmail.com");
		} catch (IllegalArgumentException e) {
			assertEquals("Customer with this email doesn't exist", e.getMessage());
		}

	}
	
	@Test
	public void getAllCustomers() {
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
		assertEquals(customer.getUsername(),customers.get(0).getUsername());
		
	}
	
	//TODO to test artists
	

}
