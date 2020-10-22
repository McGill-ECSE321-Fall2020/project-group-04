package ca.mcgill.ecse321.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.model.*;

public class PurchasingService {

	private TransactionRepository transactionRepository;

	// Getting users

	public Transaction createTransaction(int transactionID, PaymentMethod paymentMethod, DeliveryMethod deliveryMethod,
			SmartGallery smartGallery, Set<Profile> profiles, Date paymentDate, Listing listing) {
			
		String error = "";
		
		if(transactionID == 0) {
			error += "Transaction id must be non zero";
		}
		
		if(paymentMethod.equals(null)) {
			error += "Payment method must be specified";
		}
		
		if(deliveryMethod.equals(null)) {
			error += "Delivery method must be specified";
		}
		
		if(smartGallery.equals(null)) {
			error += "System must be specified";
		}
		
		if(profiles.size() == 0) {
			error += "Users must be specified";
		}
		
		if(paymentDate.equals(null)) {
			error += "Payment date must be specified";
		}
		
		if(listing.equals(null) || listing.isIsSold()) {
			error += "Listing must exist";
		}
		
		if(error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		
		Transaction transaction = new Transaction();
		transaction.setTransactionID(transactionID);
		transaction.setPaymentMethod(paymentMethod);
		transaction.setDeliveryMethod(deliveryMethod);
		transaction.setProfile(profiles);
		transaction.setPaymentDate(paymentDate);
		transaction.setListing(listing);
		transaction.setSmartGallery(smartGallery);
		transactionRepository.save(transaction);
		return transaction;
		
	}

	// Creating a transaction
	@Transactional
	public Transaction getTransactionByID(Integer transactionID) {

		if (transactionID == 0) {
			throw new IllegalArgumentException("Must provide an id");
		}

		return transactionRepository.findTransactionByTransactionID(transactionID);
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
