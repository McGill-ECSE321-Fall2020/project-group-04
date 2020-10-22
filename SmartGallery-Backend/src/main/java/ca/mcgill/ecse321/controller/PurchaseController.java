package ca.mcgill.ecse321.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.dto.TransactionDTO;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseController {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ListingRepository listingRepository;
	
	public static TransactionDTO toDTO(Transaction transaction) {
		if(transaction.equals(null)) {
			throw new IllegalArgumentException("No transaction exists");
		}

		
//		return new TransactionDTO(transaction.getSmartGallery(), transaction.getListing(), transaction.getTransactionID(),
//				transaction.getPaymentMethod(),transaction.getDeliveryMethod(),transaction.getPaymentDate());
		return null;
	}
	
	//TODO convert dto methods
	
}
