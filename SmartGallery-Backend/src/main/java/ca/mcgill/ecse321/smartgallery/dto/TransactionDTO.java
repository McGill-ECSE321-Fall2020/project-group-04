package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;

import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.Profile;

public class TransactionDTO {
	SmartGalleryDTO smartGallery;
	ListingDTO listing;
	private int transactionID;
	private PaymentMethod paymentMethod;
	private DeliveryMethod deliveryMethod;
	private Date paymentDate;
	private ProfileDTO profile;

	public TransactionDTO() {

	}

	public TransactionDTO(SmartGalleryDTO smartGallery, ListingDTO listing, ProfileDTO profile, int transactionID,
			PaymentMethod paymentMethod, DeliveryMethod deliveryMethod, Date paymentDate) {
		this.profile = profile;
		this.smartGallery = smartGallery;
		this.listing = listing;
		this.transactionID = transactionID;
		this.paymentMethod = paymentMethod;
		this.deliveryMethod = deliveryMethod;
		this.paymentDate = paymentDate;
	}

	public TransactionDTO(SmartGalleryDTO smartGallery, ListingDTO listing,int transactionID,
			PaymentMethod paymentMethod, DeliveryMethod deliveryMethod, Date paymentDate) {
		this.smartGallery = smartGallery;
		this.listing = listing;
		this.transactionID = transactionID;
		this.paymentMethod = paymentMethod;
		this.deliveryMethod = deliveryMethod;
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the smartGallery
	 */
	public SmartGalleryDTO getSmartGallery() {
		return smartGallery;
	}

	/**
	 * @return the listing
	 */
	public ListingDTO getListing() {
		return listing;
	}

	/**
	 * @return the transactionID
	 */
	public int getTransactionID() {
		return transactionID;
	}

	/**
	 * @return the paymentMethod
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @return the deliveryMethod
	 */
	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	/**
	 * @return the paymentDate
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @return the customer
	 */
	public ProfileDTO getProfile() {
		return profile;
	}

}
