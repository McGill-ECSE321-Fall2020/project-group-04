package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;

public class ArtistDTO extends ProfileDTO {
	private boolean isVerified;
	private Set<ArtworkDTO> artworks;
	
	
	public ArtistDTO() {
		
	}


	public ArtistDTO(SmartGalleryDTO smartGallery,String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate, boolean loggedIn, boolean isVerified) {
		super(smartGallery,username, password, email, defaultPaymentMethod, creationDate, loggedIn);
		this.isVerified = isVerified;
		
	}


	/**
	 * @return is the artist verified
	 */
	public boolean isVerified() {
		return isVerified;
	}


	/**
	 * add artworks
	 */
	public void addArtworks(ArtworkDTO art) {
		if(this.artworks == null) {
			this.artworks = new HashSet<>();
		}
		artworks.add(art);
	}

	

	/**
	 * @param artworks the artworks to set
	 */
	public void setArtworks(Set<ArtworkDTO> artworks) {
		this.artworks = artworks;
	}


	/**
	 * @return the artworks
	 */
	public Set<ArtworkDTO> getArtworks() {
		return artworks;
	}

	

	
	
}
