package ca.mcgill.ecse321.controller;

import java.sql.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping(value = { "/transaction", "/transaction/" })
	public TransactionDTO createTransaction(@RequestParam PaymentMethod paymentMethod,
			@RequestParam DeliveryMethod deliveryMethod,
			@PathVariable(name = "smartGallery") SmartGalleryDTO smartGalleryDTO,
			@RequestParam(name = "profiles") Set<ProfileDTO> profiles,
			@DateTimeFormat(pattern = "MM/dd/yyyy") Date paymentDate,
			@PathVariable(name = "listing") ListingDTO listing) throws IllegalArgumentException {
		
		
		
		
//		Transaction transaction = purchaseService.createTransaction(paymentMethod, deliveryMethod, smartGallery,
//				profiles, paymentDate, listing);
//		return (Converters.convertToDto(transaction));
		return null;
	}

}
