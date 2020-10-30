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

import ca.mcgill.ecse321.smartgallery.dto.TransactionDTO;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
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
	public TransactionDTO createTransaction(@RequestParam PaymentMethod paymentMethod,
			@RequestParam DeliveryMethod deliveryMethod,
			@RequestParam String username,
			@DateTimeFormat(pattern = "MM/dd/yyyy") Date paymentDate,
			@RequestParam int listingID) throws IllegalArgumentException {

		//Find objects
		Listing listing = listingService.getListing(listingID);
		SmartGallery sGallery = browsingService.getAllSmartGalleries().get(0);
		Customer customer = registrationService.getCustomer(username);
		
		Transaction transaction = purchaseService.createTransaction(paymentMethod, deliveryMethod, sGallery,
				customer, paymentDate, listing);
		return (Converters.convertToDto(transaction));
	}
	
	/**
	 * 
	 * @return all the transactions in the database
	 */
	@GetMapping(value = { "/transaction", "/transaction/" })
	public List<TransactionDTO> getTransactions(){
		return purchaseService.getAllTransactions().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param username
	 * @return a customer's transactions
	 */
	@GetMapping(value = { "/transaction/search/{username}", "transaction/search/{username}/"})
	public List<TransactionDTO> getTransactionsByCustomer(@PathVariable("username") String username){
		Customer customer = registrationService.getCustomer(username);
		return purchaseService.getTransactionByCustomer(customer).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param date
	 * @return list of transactions on a given date
	 */
	@GetMapping(value = { "/transaction/search/{date}", "/transaction/search/{date}"})
	public List<TransactionDTO> getTransactionsByDate(@PathVariable("date") Date date){
		return purchaseService.getTransactionByPaymentDate(date).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

}