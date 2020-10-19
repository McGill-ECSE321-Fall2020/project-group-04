package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;

public abstract class ProfileDTO {
	SmartGalleryDTO smartGallery;
	private String username;
	private String password;
	private String email;
	private PaymentMethod defaultPaymentMethod;
	private Date creationDate;
	
	public ProfileDTO() {
		
	}
	
	public ProfileDTO(SmartGalleryDTO smartGallery,String username, String password, String email, PaymentMethod defaultPaymentMethod,
			Date creationDate) {
		this.smartGallery = smartGallery;
		this.username = username;
		this.password = password;
		this.email = email;
		this.defaultPaymentMethod = defaultPaymentMethod;
		this.creationDate = creationDate;
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


}
