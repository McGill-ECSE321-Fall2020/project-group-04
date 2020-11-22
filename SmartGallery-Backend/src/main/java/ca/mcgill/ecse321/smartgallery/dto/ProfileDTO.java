package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;

public abstract class ProfileDTO {
	SmartGalleryDTO smartGallery;
	private String username;
	private String password;
	private String email;
	private PaymentMethod defaultPaymentMethod;
	private Date creationDate;
	private boolean loggedIn;
	private Set<TransactionDTO> transactions;
	private Set<ArtworkDTO> artworksViewed;
	

	public ProfileDTO() {
		
	}
	
	public ProfileDTO(SmartGalleryDTO smartGallery,String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate, boolean loggedIn) {
		this.smartGallery = smartGallery;
		this.username = username;
		this.password = password;
		this.email = email;
		this.defaultPaymentMethod = defaultPaymentMethod;
		this.creationDate = creationDate;
		this.loggedIn = loggedIn;
	}

	
	/**
	 * @return the smartGallery
	 */
	public SmartGalleryDTO getSmartGallery() {
		return smartGallery;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the defaultPaymentMethod
	 */
	public PaymentMethod getDefaultPaymentMethod() {
		return defaultPaymentMethod;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * @return the transaction
	 */
	public Set<TransactionDTO> getTransaction() {
		if(transactions == null) {
			transactions = new HashSet<>();
		}
		return transactions;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(Set<TransactionDTO> transaction) {
		this.transactions = transaction;
	}
	
	/**
	 * @return the artworksViewed
	 */
	public Set<ArtworkDTO> getArtworksViewed() {
		if(artworksViewed == null) {
			artworksViewed = new HashSet<>();
		}
		return artworksViewed;
	}

	/**
	 * @param artworksViewed the artworksViewed to set
	 */
	public void setArtworksViewed(Set<ArtworkDTO> artworksViewed) {
		this.artworksViewed = artworksViewed;
	}

}
