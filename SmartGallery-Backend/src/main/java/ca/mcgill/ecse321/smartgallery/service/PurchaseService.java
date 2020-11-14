package ca.mcgill.ecse321.smartgallery.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.Profile;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@Service
public class PurchaseService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ListingRepository listingRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SmartGalleryRepository smartGalleryRepository;

	@Autowired
	private ArtistRepository artistRepository;

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

			SmartGallery smartGallery, Profile profile, Date paymentDate, Listing listing) {

		String error = "";

		if (paymentMethod == null) {
			error += "Payment method must be specified\n";
		}

		if (deliveryMethod == null) {
			error += "Delivery method must be specified\n";
		}

		if (smartGallery == null) {
			error += "System must be specified\n";
		} else if (!doesSmartGalleryExist(smartGallery.getSmartGalleryID())) {
			error += "Smart gallery with that id does not exist\n";
		}

		if (profile == null) {
			error += "Profile must be specified\n";
		} else if (!doesProfileExist(profile.getUsername())) {
			error += "Profile with that username does not exist\n";
		}

		if (paymentDate == null) {
			error += "Payment date must be specified\n";
		}

		if (listing == null) {
			error += "Listing must exist\n";
		} else if (!doesListingExist(listing.getListingID())) {
			error += "Listing with this id does not exist\n";
		} else if (listing.isIsSold()) {
			error += "Listing has already been sold\n";
		}else if (listing.getArtwork() != null && listing.getArtwork().getArtists() != null
				&& listing.getArtwork().getArtists().contains(profile)) {
			error += "Artist cannot purchase their own artwork\n";
		}

		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		Transaction transaction = new Transaction();
		transaction.setTransactionID(profile.getUsername().hashCode() * listing.getListingID());
		transaction.setPaymentMethod(paymentMethod);
		transaction.setDeliveryMethod(deliveryMethod);
		transaction.setProfile(profile);
		HashSet<Transaction> tSet = new HashSet<>();
		if (profile.getTransaction() != null) {
			tSet = new HashSet<>(profile.getTransaction());
		}
		tSet.add(transaction);
		profile.setTransaction(tSet);
		transaction.setPaymentDate(paymentDate);
		transaction.setListing(listing);
		listing.setIsSold(true);
		listing.setTransaction(transaction);
		transaction.setSmartGallery(smartGallery);
		tSet = new HashSet<Transaction>();
		if (smartGallery.getTransaction() != null) {
			tSet = new HashSet<>(smartGallery.getTransaction());
		}
		tSet.add(transaction);
		smartGallery.setTransaction(tSet);
		transactionRepository.save(transaction);
		if (profile instanceof Customer) {
			Customer c = (Customer) profile;
			customerRepository.save(c);
		} else {
			Artist a = (Artist) profile;
			artistRepository.save(a);
		}
		listingRepository.save(listing);
		smartGalleryRepository.save(smartGallery);
		return transaction;

	}

	/**
	 * @param transactionID
	 * @return transaction associated to this id
	 */
	@Transactional
	public Transaction getTransactionByID(int transactionID) {

		Transaction transaction = transactionRepository.findTransactionByTransactionID(transactionID);

		if (transaction == null) {
			throw new IllegalArgumentException("No transaction is associated to this id");
		}

		return transaction;
	}

	/**
	 * 
	 * @param customer
	 * @return a list of the customer's transactions
	 */
	@Transactional
	public List<Transaction> getTransactionByProfile(Profile profile) {
		if (profile == null) {
			throw new IllegalArgumentException("Must provide a valid customer");
		}

		List<Transaction> transactions = transactionRepository.findTransactionByProfile(profile);

		if (transactions == null) {
			throw new IllegalArgumentException("This customer does not have any associated transactions");
		}

		return transactions;
	}

	/**
	 * 
	 * @param date
	 * @return a list of transactions that happened on a given date
	 */
	@Transactional
	public List<Transaction> getTransactionByPaymentDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("No date has been provided");
		}

		List<Transaction> transactions = transactionRepository.findTransactionByPaymentDate(date);

		if (transactions == null) {
			throw new IllegalArgumentException("No transactions exist on this date");
		}
		// TODO Check for date format
		return transactions;
	}

	/**
	 * 
	 * @return all the transaction in the system
	 */
	@Transactional
	public List<Transaction> getAllTransactions() {
		return toList(transactionRepository.findAll());
	}

	private boolean doesProfileExist(String name) {
		// Uses existing method in artist repository to find an artist by username
		Profile p = artistRepository.findArtistByUsername(name);

		if (p == null) {
			p = customerRepository.findCustomerByUsername(name);
		}

		return p != null;
	}

	private boolean doesListingExist(int listingID) {
		Listing listing = listingRepository.findListingByListingID(listingID);
		return listing != null;
	}

	private boolean doesSmartGalleryExist(int smartGalleryID) {
		SmartGallery sg = smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID);
		return sg != null;
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
