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

			SmartGallery smartGallery, Customer customer, Date paymentDate, Listing listing) {


		String error = "";

		if (paymentMethod == null) {
			error += "Payment method must be specified";
		}

		if (deliveryMethod == null) {
			error += "Delivery method must be specified";
		}

		if (smartGallery == null) {
			error += "System must be specified";
		}

		if (customer == null) {
			error += "Customers/Artists must be specified";
		}

		if (paymentDate == null) {
			error += "Payment date must be specified";
		}

		if (listing == null || listing.isIsSold()) {
			error += "Listing must exist";
		}

		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}


		Transaction transaction = new Transaction();
		transaction.setTransactionID(customer.getUsername().hashCode() * listing.getListingID());
		transaction.setPaymentMethod(paymentMethod);
		transaction.setDeliveryMethod(deliveryMethod);
		transaction.setCustomer(customer);
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

	/**
	 * 
	 * @param customer
	 * @return a list of the customer's transactions
	 */
	@Transactional
	public List<Transaction> getTransactionByCustomer(Customer customer) {
		if(customer == null) {
			throw new IllegalArgumentException("Must provide a valid customer");
		}
		
		return transactionRepository.findTransactionByCustomer(customer);
	}
	
	/**
	 * 
	 * @param date
	 * @return a list of transactions that happened on a given date
	 */
	@Transactional
	public List<Transaction> getTransactionByPaymentDate(Date date){
		if(date == null) {
			throw new IllegalArgumentException("No date has been provided");
		}
		
		//TODO Check for date format
		
		
		return transactionRepository.findTransactionByPaymentDate(date);
	}
	
	/**
	 * 
	 * @return all the transaction in the system
	 */
	@Transactional
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