package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;
import java.util.Set;

import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;

public class CustomerDTO extends ProfileDTO {
	
	private Set<ArtworkDTO> artworkViewed;
	private Set<TransactionDTO> transaction;
	
	public CustomerDTO() {
		
	}

	public CustomerDTO(SmartGalleryDTO smartGallery,String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate, boolean loggedIn) {
		super(smartGallery,username, password, email, defaultPaymentMethod, creationDate, loggedIn);
	}

	/**
	 * @param artworkViewed the artworkViewed to set
	 */
	public void setArtworkViewed(Set<ArtworkDTO> artworkViewed) {
		this.artworkViewed = artworkViewed;
	}

	/**
	 * @return the artworkViewed
	 */
	public Set<ArtworkDTO> getArtworkViewed() {
		return artworkViewed;
	}
	

}
