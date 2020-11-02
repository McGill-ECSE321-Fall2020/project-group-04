package ca.mcgill.ecse321.smartgallery.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.dto.TransactionDTO;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;
import ca.mcgill.ecse321.smartgallery.service.BrowsingService;
import ca.mcgill.ecse321.smartgallery.service.ListingService;
import ca.mcgill.ecse321.smartgallery.service.PurchaseService;
import ca.mcgill.ecse321.smartgallery.service.RegistrationService;

/**
 * 
 * @author Viet Tran
 *
 */
@CrossOrigin(origins = "*")
@RestController
public class PurchaseController {
	
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

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ListingService listingService;

	@Autowired
	private BrowsingService browsingService;

	@Autowired
	private RegistrationService registrationService;

	/**
	 * 
	 * @param paymentMethod
	 * @param deliveryMethod
	 * @param username
	 * @param paymentDate
	 * @param listingID
	 * @return
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/transaction", "/transaction/" })
	public TransactionDTO createTransaction(@RequestParam("paymentMethod") String paymentMethod,
			@RequestParam("deliveryMethod") String deliveryMethod, @RequestParam("username") String username,
			@RequestParam("listingID") int listingID)
			throws IllegalArgumentException {

		// Find objects
		Listing listing = listingService.getListingByID(listingID);
		SmartGallery sGallery = browsingService.getAllSmartGalleries().get(0);
		Customer customer = registrationService.getCustomer(username);
		long millis = System.currentTimeMillis();
		Date paymentDate = new java.sql.Date(millis);
		Transaction transaction = purchaseService.createTransaction(
				Converters.convertStringToPaymentMethod(paymentMethod),
				Converters.convertStringToDeliveryMethod(deliveryMethod), sGallery, customer, paymentDate, listing);
		return (Converters.convertToDto(transaction));
	}

	/**
	 * 
	 * @return all the transactions in the database
	 */
	@GetMapping(value = { "/transaction", "/transaction/" })
	public List<TransactionDTO> getTransactions() {
		return purchaseService.getAllTransactions().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return transaction corresponding to that id
	 */
	@GetMapping(value = { "/transaction/{id}", "/transaction/{id}/" })
	public TransactionDTO getTransactionById(@PathVariable("id") int transactionID) {
		Transaction transaction = purchaseService.getTransactionByID(transactionID);
		return Converters.convertToDto(transaction);
	}

	/**
	 * 
	 * @param username
	 * @return a customer's transactions
	 */
	@GetMapping(value = { "/transaction/search/username/{username}",
			"transaction/search/username/{username}/" })
	public List<TransactionDTO> getTransactionsByCustomer(@PathVariable("username") String username) {
		Customer customer = registrationService.getCustomer(username);
		return purchaseService.getTransactionByCustomer(customer).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param date
	 * @return list of transactions on a given date
	 */
	@GetMapping(value = { "/transaction/searchdate/{date}", "/transaction/searchdate/{date}" })
    public List<TransactionDTO> getTransactionsByDate(@PathVariable("date") String date) {
        return purchaseService.getTransactionByPaymentDate(Date.valueOf(date)).stream().map(p -> Converters.convertToDto(p))
                .collect(Collectors.toList());
    }

}
