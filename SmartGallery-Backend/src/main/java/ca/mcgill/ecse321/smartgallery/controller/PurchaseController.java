package ca.mcgill.ecse321.smartgallery.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartgallery.dto.TransactionDTO;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.Profile;
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
	private PurchaseService purchaseService;

	@Autowired
	private ListingService listingService;

	@Autowired
	private BrowsingService browsingService;

	@Autowired
	private RegistrationService registrationService;

	/**
	 * responds to transaction request
	 * 
	 * @param paymentMethod
	 * @param deliveryMethod
	 * @param username
	 * @param paymentDate
	 * @param listingID
	 * @return TransactionDTO
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
		Profile p = registrationService.getProfile(username);
		long millis = System.currentTimeMillis();
		Date paymentDate = new java.sql.Date(millis);
		Transaction transaction = purchaseService.createTransaction(
				Converters.convertStringToPaymentMethod(paymentMethod),
				Converters.convertStringToDeliveryMethod(deliveryMethod), sGallery, p, paymentDate, listing);
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
	 * @return a Profiles's transactions
	 */
	@GetMapping(value = { "/transaction/search/username/{username}",
			"transaction/search/username/{username}/" })
	public List<TransactionDTO> getTransactionsByCustomer(@PathVariable("username") String username) {
		Profile profile = registrationService.getProfile(username);
		return purchaseService.getTransactionByProfile(profile).stream().map(p -> Converters.convertToDto(p))
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
