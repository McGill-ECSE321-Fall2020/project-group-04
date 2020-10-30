package ca.mcgill.ecse321.smartgallery.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.model.*;

@Service
public class PurchaseService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ListingRepository listingRepository;


	/**
	 * Method that creates a transaction and sets the id based on the
	 * listing/customer username
	 * 
	 * @param paymentMethod
	 * @param deliveryMethod
	 * @param smartGallery
	 * @param profiles
	 * @param paymentDate
	 * @param listing
	 * @return Transaction transaction
	 */
	@Transactional
	public Transaction createTransaction(PaymentMethod paymentMethod, DeliveryMethod deliveryMethod,
			SmartGallery smartGallery, Set<Profile> profiles, Date paymentDate, Listing listing) {

		// Find customer id
		String error = "";

		if (paymentMethod.equals(null)) {
			error += "Payment method must be specified";
		}

		if (deliveryMethod.equals(null)) {
			error += "Delivery method must be specified";
		}

		if (smartGallery.equals(null)) {
			error += "System must be specified";
		}

		if (profiles.size() == 0) {
			error += "Customers/Artists must be specified";
		}

		if (paymentDate.equals(null)) {
			error += "Payment date must be specified";
		}

		if (listing.equals(null) || listing.isIsSold()) {
			error += "Listing must exist";
		}

		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		String customerID = "";

		for (Profile p : profiles) {
			if (p instanceof Customer) {
				customerID += p.getUsername();
			}
		}

		Transaction transaction = new Transaction();
		transaction.setTransactionID(customerID.hashCode() * listing.getListingID());
		transaction.setPaymentMethod(paymentMethod);
		transaction.setDeliveryMethod(deliveryMethod);
		transaction.setProfile(profiles);
		transaction.setPaymentDate(paymentDate);
		transaction.setListing(listing);
		listing.setIsSold(true);
		listingRepository.save(listing);
		transaction.setSmartGallery(smartGallery);
		transactionRepository.save(transaction);
		return transaction;

	}
	
	/**
	 * @param transactionID
	 * @return transaction associated to this id
	 */
	@Transactional
	public Transaction getTransactionByID(String customerName, Integer listingID) {
	
		if(customerName == null) {
			throw new IllegalArgumentException("Must provide valid customer username");
		}
		
		if (listingID == null) {
			throw new IllegalArgumentException("Must provide an id");
		}

		return transactionRepository.findTransactionByTransactionID(customerName.hashCode() * listingID);
	}

	public List<Transaction> getAllTransactions() {
		return toList(transactionRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
