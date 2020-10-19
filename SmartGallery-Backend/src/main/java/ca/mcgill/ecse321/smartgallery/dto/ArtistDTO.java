package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;

public class ArtistDTO extends ProfileDTO {
	private boolean isVerified;
	
	
	public ArtistDTO() {
		
	}


	public ArtistDTO(SmartGalleryDTO smartGallery,String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate, Boolean isVerified) {
		super(smartGallery,username, password, email, defaultPaymentMethod, creationDate);
		this.isVerified = isVerified;
	}


	/**
	 * @return is the artist verified
	 */
	public boolean isVerified() {
		return isVerified;
	}
	
}
