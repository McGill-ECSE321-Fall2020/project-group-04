package ca.mcgill.ecse321.smartgallery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTests {

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
	private PurchaseService purchaseService;

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

	// Listing variables
	private static final boolean SOLD = true;
	private static final boolean NOT_SOLD = false;
	private static final Date DATE_LISTED = Date.valueOf("2020-11-25");
	private static final int L_ID = 321;
	private static final int L_ID2 = 9393;

	// SmartGallery variables
	private static final int SG_ID = 1234;

	// Transaction variables
	private static final Date PAYMENT_DATE = Date.valueOf("2020-08-22");
	private static final DeliveryMethod DELIVERY_METHOD = DeliveryMethod.PICKUP;
	private static final int T_ID = CUSTOMER_USERNAME.hashCode() * L_ID;
	private static Customer CUSTOMER1;

	private static final Date WRONG_DATE = Date.valueOf("2018-05-06");

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

		// Listing creation
		lenient().when(listingRepository.findListingByListingID(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(L_ID)) {
				Listing listing = new Listing();
				listing.setIsSold(NOT_SOLD);
				listing.setListedDate(DATE_LISTED);
				listing.setListingID(L_ID);
				return listing;
			} else if (invocation.getArgument(0).equals(L_ID2)) {
				Listing listing = new Listing();
				listing.setIsSold(SOLD);
				listing.setListedDate(DATE_LISTED);
				listing.setListingID(L_ID);
				return listing;
			} else {
				return null;
			}
		});

		// TODO set links?
		lenient().when(transactionRepository.findTransactionByTransactionID(anyInt()))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(T_ID)) {
						Transaction transaction = new Transaction();
						transaction.setTransactionID(T_ID);
						return transaction;
					} else {
						return null;
					}

				});

		// TODO set links?
		lenient().when(transactionRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			List<Transaction> transactions = new ArrayList<>();
			Transaction transaction = new Transaction();
			transaction.setTransactionID(T_ID);
			transactions.add(transaction);
			return transactions;
		});

		lenient().when(transactionRepository.findTransactionByProfile(any(Customer.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(CUSTOMER1)) {
						List<Transaction> transactions = new ArrayList<>();
						Transaction transaction = new Transaction();
						transaction.setTransactionID(T_ID);
						transaction.setProfile(CUSTOMER1);
						transactions.add(transaction);
						CUSTOMER1.setTransaction(new HashSet<>(transactions));
						return transactions;
					} else {
						return null;
					}
				});

		lenient().when(transactionRepository.findTransactionByPaymentDate(any(Date.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (invocation.getArgument(0).equals(PAYMENT_DATE)) {
						List<Transaction> transactions = new ArrayList<>();
						Transaction transaction = new Transaction();
						transaction.setTransactionID(T_ID);
						transaction.setPaymentDate(PAYMENT_DATE);
						transactions.add(transaction);
						return transactions;
					} else {
						return null;
					}
				});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
		lenient().when(transactionRepository.save(any(Transaction.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(listingRepository.save(any(Listing.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(smartGalleryRepository.save(any(SmartGallery.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateTransaction() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);

		Transaction transaction = null;
		try {
			transaction = purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer,
					PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		assertNotNull(transaction);
		assertEquals(transaction.getTransactionID(), T_ID);
	}

	@Test
	public void testCreateTransactionNoCustomer() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, null, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Profile must be specified\n", e.getMessage());
		}
	}
	
	@Test
	public void testCreateTransactionInvalidCustomer() {
		Customer customer = new Customer();
		customer.setUsername("invalid customer");
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Profile with that username does not exist\n", e.getMessage());
		}
	}

	@Test
	public void testCreateTransactionNoSmartGallery() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, null, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("System must be specified\n", e.getMessage());
		}
	}
	
	@Test
	public void testCreateTransactionInvalidSmartGallery() {
		SmartGallery smartGallery = new SmartGallery();
		smartGallery.setSmartGalleryID(8383);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, smartGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Smart gallery with that id does not exist\n", e.getMessage());
		}
	}

	@Test
	public void testCreateTransactionNoListing() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, null);
		} catch (IllegalArgumentException e) {
			assertEquals("Listing must exist\n", e.getMessage());
		}
	}

	@Test
	public void testCreateTransactionInvalidListing() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Listing listing = new Listing();
		listing.setListingID(3949);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Listing with this id does not exist\n", e.getMessage());
		}
	}

	@Test
	public void testCreateTransactionNoPaymentDate() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, null, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Payment date must be specified\n", e.getMessage());
		}
	}

	@Test
	public void testCreateTransactionNoPaymentMethod() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			purchaseService.createTransaction(null, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Payment method must be specified\n", e.getMessage());
		}
	}

	@Test
	public void testCreateTransactionNoDeliveryMethod() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			purchaseService.createTransaction(DEFAULTPAY, null, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Delivery method must be specified\n", e.getMessage());
		}
	}

	@Test
	public void testCreateTransactionForSoldListing() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID2);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			assertEquals("Listing has already been sold\n", e.getMessage());
		}
	}

	@Test
	public void testGetTransactionID() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);

		Transaction transaction = null;
		try {
			transaction = purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer,
					PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		Transaction transaction2 = purchaseService.getTransactionByID(transaction.getTransactionID());
		assertEquals(transaction.getTransactionID(), transaction2.getTransactionID());
	}

	@Test
	public void testGetAllTransactions() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);

		Transaction transaction = null;
		try {
			transaction = purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer,
					PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Transaction> transactionList = purchaseService.getAllTransactions();
		assertEquals(transaction.getTransactionID(), transactionList.get(0).getTransactionID());
	}

	@Test
	public void testGetTransactionByCustomer() {
		CUSTOMER1 = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);

		Transaction transaction = null;
		try {
			transaction = purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, CUSTOMER1,
					PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Transaction> transactionList = purchaseService.getTransactionByProfile(CUSTOMER1);
		assertEquals(transaction.getTransactionID(), transactionList.get(0).getTransactionID());
	}

	@Test
	public void testGetTransactionNoCustomer() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);

		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			purchaseService.getTransactionByProfile(null);
		} catch (IllegalArgumentException e) {
			assertEquals("Must provide a valid customer", e.getMessage());
		}

	}

	@Test
	public void testGetTransactionWrongCustomer() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Customer customer2 = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME2);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			purchaseService.getTransactionByProfile(customer2);
		} catch (IllegalArgumentException e) {
			assertEquals("This customer does not have any associated transactions", e.getMessage());
		}

	}

	@Test
	public void testGetTransactionByDate() {
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);

		Transaction transaction = null;
		try {
			transaction = purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer,
					PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		List<Transaction> transactionList = purchaseService.getTransactionByPaymentDate(PAYMENT_DATE);
		assertEquals(transaction.getTransactionID(), transactionList.get(0).getTransactionID());
		assertEquals(transaction.getPaymentDate(), transactionList.get(0).getPaymentDate());
	}

	@Test
	public void testGetTransactionNoDate() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			purchaseService.getTransactionByPaymentDate(WRONG_DATE);
		} catch (IllegalArgumentException e) {
			assertEquals("No transactions exist on this date", e.getMessage());
		}
	}

	@Test
	public void testGetTransactionWrongDate() {
		Listing listing = listingRepository.findListingByListingID(L_ID);
		SmartGallery sGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(SG_ID);
		Customer customer = customerRepository.findCustomerByUsername(CUSTOMER_USERNAME);
		try {
			purchaseService.createTransaction(DEFAULTPAY, DELIVERY_METHOD, sGallery, customer, PAYMENT_DATE, listing);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			purchaseService.getTransactionByPaymentDate(null);
		} catch (IllegalArgumentException e) {
			assertEquals("No date has been provided", e.getMessage());
		}
	}

}
