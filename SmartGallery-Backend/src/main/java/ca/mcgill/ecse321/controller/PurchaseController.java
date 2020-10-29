package ca.mcgill.ecse321.controller;

import java.sql.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.service.PurchaseService;
import ca.mcgill.ecse321.smartgallery.dto.ListingDTO;
import ca.mcgill.ecse321.smartgallery.dto.ProfileDTO;
import ca.mcgill.ecse321.smartgallery.dto.SmartGalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.TransactionDTO;
import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@CrossOrigin(origins = "*")
@RestController
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	public TransactionDTO createTransaction(@RequestParam PaymentMethod paymentMethod, @RequestParam DeliveryMethod deliveryMethod, 
			@PathVariable(name = "smartGallery") SmartGalleryDTO smartGalleryDTO, @RequestParam(name = "profiles") Set<ProfileDTO> profiles,
			@RequestParam Date paymentDate, @PathVariable(name = "listing") ListingDTO listing) throws IllegalArgumentException {
//		Transaction transaction = purchaseService.createTransaction(paymentMethod, deliveryMethod, smartGallery, profiles, paymentDate, listing);
//		return(toDTO(transaction));
		return null;
	}
	
	
	
	
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
