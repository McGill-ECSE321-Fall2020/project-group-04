package ca.mcgill.ecse321.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.*;
import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.Profile;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

public class SmartGalleryService {
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private ArtworkRepository artworkRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private ListingRepository listingRepository;
	@Autowired
	private SmartGalleryRepository smartGalleryRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	// ??? not sure how to make
	@Transactional
	public void promoteArtwork(Artwork artwork) {
		artwork.setIsBeingPromoted(true);
	}

	@Transactional
	public HashSet<Artist> searchArtist(SmartGallery smartGallery, String searchInput) {
		HashSet<Artist> results = new HashSet<>();
		for (Profile profile : smartGallery.getProfile()) {
			if (profile instanceof Artist && profile.getUsername().toLowerCase().replaceAll("\\s+", "")
					.contains(searchInput.toLowerCase().replaceAll("\\s+", ""))) {
				results.add((Artist) profile);
			}
		}
		return results;
	}

	@Transactional
	public HashSet<Listing> searchArtwork(Gallery gallery, String searchInput, double minPrice, double maxPrice,
			ArtStyle artStyle) {
		HashSet<Listing> results = new HashSet<>();
		for (Listing listing : gallery.getListing()) {
			Artwork artwork = listing.getArtwork();
			if (artwork.getName().toLowerCase().replaceAll("\\s+", "")
					.contains(searchInput.toLowerCase().replaceAll("\\s+", "")) && artwork.getPrice() >= minPrice
					&& artwork.getPrice() <= maxPrice && artwork.getStyle().equals(artStyle)) {
				results.add(listing);
			}
		}
		return results;
	}

	@Transactional
	public HashSet<Listing> searchArtwork(Gallery gallery, String searchInput, double minPrice, double maxPrice) {
		HashSet<Listing> results = new HashSet<>();
		for (Listing listing : gallery.getListing()) {
			Artwork artwork = listing.getArtwork();
			if (artwork.getName().toLowerCase().replaceAll("\\s+", "")
					.contains(searchInput.toLowerCase().replaceAll("\\s+", "")) && artwork.getPrice() >= minPrice
					&& artwork.getPrice() <= maxPrice) {
				results.add(listing);
			}
		}
		return results;
	}

	@Transactional
	public HashSet<Listing> searchArtwork(Gallery gallery, String searchInput, ArtStyle artStyle) {
		HashSet<Listing> results = new HashSet<>();
		for (Listing listing : gallery.getListing()) {
			Artwork artwork = listing.getArtwork();
			if (artwork.getName().toLowerCase().replaceAll("\\s+", "").contains(
					searchInput.toLowerCase().replaceAll("\\s+", "")) && artwork.getStyle().equals(artStyle)) {
				results.add(listing);
			}
		}
		return results;
	}

	@Transactional
	public HashSet<Listing> searchArtwork(Gallery gallery, String searchInput) {
		HashSet<Listing> results = new HashSet<>();
		for (Listing listing : gallery.getListing()) {
			Artwork artwork = listing.getArtwork();
			if (artwork.getName().toLowerCase().replaceAll("\\s+", "")
					.contains(searchInput.toLowerCase().replaceAll("\\s+", ""))) {
				results.add(listing);
			}
		}
		return results;
	}

	@Transactional
	public void addToBrowseHistory(Customer customer, Artwork artwork) {
		Set<Artwork> viewedArtworks = customer.getArtworksViewed();
		viewedArtworks.add(artwork);
		customer.setArtworksViewed(viewedArtworks);
	}

	@Transactional
	public Set<Artwork> viewBrowsingHistory(Customer customer) {
		return customer.getArtworksViewed();
	}

	/**
	 * @author Stavros Mitsoglou The following parameters are those concerning an
	 *         Artwork
	 * @param name
	 * @param year
	 * @param price
	 * @param isBeingPromoted
	 * @param style
	 * @param height
	 * @param weight
	 * @param width
	 * @param artists
	 * @param gallery
	 * @return
	 * 
	 *         This method creates and saves an Artwork.
	 */
	@Transactional
	public Artwork createArtwork(String name, int year, double price, boolean isBeingPromoted, ArtStyle style,
			int height, int weight, int width, Set<Artist> artists, Gallery gallery) {
		Artwork artwork = new Artwork();
		artwork.setName(name);
		artwork.setYear(year);
		artwork.setPrice(price);
		artwork.setPrice(price);
		artwork.setIsBeingPromoted(isBeingPromoted);
		artwork.setStyle(style);
		artwork.setHeight(height);
		artwork.setWeight(weight);
		artwork.setWidth(width);
		artwork.setArtists(artists);
		artwork.setGallery(gallery);
		artwork.setArtworkID(artists.iterator().next().getUsername().hashCode() * gallery.getGalleryName().hashCode());
		artworkRepository.save(artwork);
		return artwork;
	}

	/**
	 * @author Stavros Mitsoglou
	 * @param artwork    Artwork related to the listing
	 * @param dateListed listed date
	 * @param gallery    gallery related to the artwork
	 * @return listing created
	 * 
	 *         This method creates an initial listing that is currently for sale
	 *         (not sold). No transaction has been done as of yet. the listing id is
	 *         created using the hashcode of the artwork name and the username of
	 *         the artist (to make it unique)
	 */
	@Transactional
	public Listing createListing(Artwork artwork, Date dateListed, double price, Gallery gallery) {

		Listing listing = new Listing();
		listing.setListingID(artwork.getArtworkID() * artwork.getArtists().iterator().next().getUsername().hashCode());
		listing.setIsSold(false);
		listing.setArtwork(artwork);
		listing.setListedDate(dateListed);
		listing.setGallery(gallery);
		listingRepository.save(listing);
		artwork.setListing(listing);
		artwork.setPrice(price);
		artworkRepository.save(artwork);
		return listing;

	}

	/**
	 * @author Stavros Mitsoglou
	 * @param listing Listing where the artwork details are being updated the
	 *                following parameters are some of the artwork fields that can
	 *                be updated
	 * @param name
	 * @param year
	 * @param price
	 * @param style
	 * @param height
	 * @param width
	 * @param weight
	 * @return This method will update the above characteristics of the artwork in
	 *         question. Note that the artist, gallery, and artwork ID should not
	 *         change.
	 */

	@Transactional
	public Artwork updateArtworkListed(Listing listing, String name, int year, double price, ArtStyle style, int height,
			int width, int weight) {
		Artwork artwork = listing.getArtwork();
		artwork.setName(name);
		artwork.setYear(year);
		artwork.setPrice(price);
		artwork.setStyle(style);
		artwork.setHeight(height);
		artwork.setWidth(width);
		artwork.setWeight(weight);
		artworkRepository.save(artwork);

		return artwork;
	}

	@Transactional
	/**
	 * @author Stavros Mitsoglou
	 * @param listing -> listing we are trying to find
	 * @return Method will return true if there is a duplicate listing
	 */
	public boolean foundListing(Listing listing) {
		boolean found = true;
		Listing l = getListing(listing.getListingID());
		if (l == null)
			found = false;

		return found;
	}

	/**
	 * @author Stavros Mitsoglou
	 * @param listingID
	 * @return listing corresponding to the listing id
	 * 
	 *         This method returns a listing from its id.
	 */
	@Transactional
	public Listing getListing(int listingID) {

		return listingRepository.findListingByListingID(listingID);
	}

	/**
	 * @author Stavros Mitsoglou
	 * @return all listings Return all listings in repository.
	 */
	@Transactional

	public List<Listing> getAllListings() {
		return toList(listingRepository.findAll());
	}

	
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

		// Should transaction be directly associated to customer since artist is already
		// present in listing?
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
		transaction.setSmartGallery(smartGallery);
		transactionRepository.save(transaction);
		return transaction;

	}
	
	
	@Transactional
	public void changeListingStatus(Listing listing) {
		if (listing.equals(null)) {
			throw new IllegalArgumentException("The provided listing was invalid");
		}

		listing.setIsSold(!listing.isIsSold());
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
		
		if (listingID == 0) {
			throw new IllegalArgumentException("Must provide an id");
		}

		return transactionRepository.findTransactionByTransactionID(customerName.hashCode() * listingID);
	}

	/**
	 * 
	 * @return All listings that are currently for sale (isSold=false)
	 */
	@Transactional
	public List<Listing> getListingForSale() {
		List<Listing> listings = new ArrayList<>();

		for (Listing l : getAllListings()) {
			if (!l.isIsSold()) {
				listings.add(l);
			}
		}
		return listings;
	}

	/**
	 * 
	 * @return all the transactions currently in the database
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
