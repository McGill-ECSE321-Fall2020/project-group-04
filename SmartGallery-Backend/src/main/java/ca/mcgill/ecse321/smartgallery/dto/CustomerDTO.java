package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;

public class CustomerDTO extends ProfileDTO {
	
	public CustomerDTO() {
		
	}

	public CustomerDTO(SmartGalleryDTO smartGallery,String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate) {
		super(smartGallery,username, password, email, defaultPaymentMethod, creationDate);
	}
}
