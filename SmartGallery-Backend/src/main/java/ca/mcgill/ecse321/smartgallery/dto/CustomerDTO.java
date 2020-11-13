package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;
import java.util.List;

import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;

public class CustomerDTO extends ProfileDTO {
	
	private List<ArtworkDTO> artworkViewed;
	private List<TransactionDTO> transaction;
	
	public CustomerDTO() {
		
	}

	public CustomerDTO(SmartGalleryDTO smartGallery,String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate, boolean loggedIn) {
		super(smartGallery,username, password, email, defaultPaymentMethod, creationDate, loggedIn);
	}

	/**
	 * @param artworkViewed the artworkViewed to set
	 */
	public void setArtworkViewed(List<ArtworkDTO> artworkViewed) {
		this.artworkViewed = artworkViewed;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(List<TransactionDTO> transaction) {
		this.transaction = transaction;
	}
	
	
}
