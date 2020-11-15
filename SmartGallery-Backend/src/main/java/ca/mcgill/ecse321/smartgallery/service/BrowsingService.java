package ca.mcgill.ecse321.smartgallery.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.Profile;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;

@Service
public class BrowsingService {

	@Autowired
	private ArtworkRepository artworkRepository;
	
	@Autowired
	private GalleryRepository galleryRepository;
	
	@Autowired
	private SmartGalleryRepository smartGalleryRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ArtistRepository artistRepository;

	/**
	 * 
	 * Method that creates a SmartGallery given a SmartGalleryID.
	 * 
	 * @authors OliverStappas, ZachS
	 * 
	 * @param smartGalleryID ID for the SmartGallery
	 * @return SmartGallery The SmartGallery created with the ID
	 */
	@Transactional
	public SmartGallery createSmartGallery(int smartGalleryID) {
		// If the provided ID has an illegal value of 0
		if (smartGalleryID == 0) {
			throw new IllegalArgumentException("SmartGallery ID must not be zero");
		}
		// If a smartGallery with that ID already exists
		if (validateSmartGalleryID(smartGalleryID)) {
			throw new IllegalArgumentException("A smartGallery with that ID already exists");
		}

		// Creating the gallery with the provided ID and saving it in the smartGalleryRepository
		SmartGallery smartGallery = new SmartGallery();
		smartGallery.setSmartGalleryID(smartGalleryID);
		smartGalleryRepository.save(smartGallery);
		return smartGallery;
	}
	
	/**
	 * 
	 * Method that checks whether or not a SmartGallery with the provided ID already exists.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param smartGalleryID ID for the SmartGallery
	 * @return boolean whether at smartGallery with that ID exists or not
	 */
	private boolean validateSmartGalleryID(int smartGalleryID) {
		// If that smartGallery does already not exist
		if (smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Method that returns a list of all the SmartGalleries in the smartGallery repository.
	 * 
	 * @author ZachS 
	 * 
	 * @return A list of all SmartGalleries
	 */
	@Transactional
	public List<SmartGallery> getAllSmartGalleries() {
		return toList(smartGalleryRepository.findAll());
	}

	/**
	 * 
	 * Method that returns a smartGallery that has the given ID.
	 * 
	 * @authors OliverStappas, ZachS
	 * 
	 * @param smartGalleryID ID for the SmartGallery
	 * @return A SmartGallery with the ID provided from the smartGalleryRepository
	 * 
	 */
	@Transactional
	public SmartGallery getSmartGalleryByID(int smartGalleryID) {
		// Uses existing method in smartGallery repository to find a smartGallery by ID
		SmartGallery smartGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID);

		// If the smartGallery doesn't exist, throw an error
		if (smartGallery == null) {
			throw new IllegalArgumentException("SmartGallery doesn't exist");
		}

		// Otherwise return the found smartGallery
		return smartGallery;
	}

	/**
	 * 
	 * Method that creates a Gallery given a galleryName, commission percentage
	 * and SmartGallery.
	 * 
	 * @authors OliverStappas, ZachS
	 * 
	 * @param galleryName name for the Gallery
	 * @param commission  percentage of commission the gallery receives on transactions
	 * @param smartGallery the SmartGallery the gallery will be contained in

	 * @return Gallery The Gallery created with the provided parameters
	 * 
	 */
	@Transactional
	public Gallery createGallery(String galleryName, SmartGallery smartGallery, double commission) {
		// Checking if ID exists already
		if (validateGalleryName(galleryName)) {
			throw new IllegalArgumentException("A gallery with that name already exists");
		}
		if (galleryName == null || galleryName == "" || galleryName.trim().length() == 0) {
			throw new IllegalArgumentException("Gallery Name cannot be empty");
		}
		if (commission < 0 || commission > 100) {
			throw new IllegalArgumentException("The commission percentage must be between 0 and 100 inclusive");
		}
		if (smartGallery == null) {
			throw new IllegalArgumentException("A SmartGallery is need to create a Gallery");
		}
		
		// Creating the gallery with the provided galleryName, commission percentage and smartGallery,
		// and saving it in the galleryRepository
		Gallery gallery = new Gallery();
		gallery.setGalleryName(galleryName);
		gallery.setSmartGallery(smartGallery);
		smartGallery.setGallery(gallery);
		smartGalleryRepository.save(smartGallery);
		gallery.setComissionPercentage(commission);
		galleryRepository.save(gallery);
		return gallery;
	}
	
	/**
	 * 
	 * Method that checks whether or not a Gallery with the provided galleryName already exists.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param galleryName galleryName for the Gallery
	 * @return boolean Whether at Gallery with that galleryName exists or not
	 * 
	 */
	private boolean validateGalleryName(String galleryName) {
		if (galleryRepository.findGalleryByGalleryName(galleryName) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * Method that returns a list of all the Galleries in the gallery repository.
	 * 
	 * @author ZachS 
	 * 
	 * @return List<Gallery> A list of all Galleries
	 */
	@Transactional
	public List<Gallery> getAllGalleries() {
		return toList(galleryRepository.findAll());
	}
	
	/**
	 * 
	 * Method that returns a Gallery that has the given galleryName.
	 * 
	 * @authors OliverStappas, ZachS
	 * 
	 * @param galleryName galleryName for the Gallery
	 * @return Gallery A Gallery with the galleryName provided from the galleryRepository
	 * 
	 */
	@Transactional
	public Gallery getGalleryByName(String galleryName) {
		if (galleryName == null || galleryName == "" || galleryName.trim().length() == 0) {
			throw new IllegalArgumentException("Gallery Name cannot be empty");
		}
		
		// Uses existing method in gallery repository to find a gallery by name
		Gallery gallery = galleryRepository.findGalleryByGalleryName(galleryName);

		// If the gallery doesn't exist, throw an error
		if (gallery == null) {
			throw new IllegalArgumentException("Gallery doesn't exist");
		}

		// Otherwise return the found gallery
		return gallery;
	}
	
	/**
	 * 
	 * Method that promotes a given artwork.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param Artwork Artwork to promote
	 * @return Artwork Artwork that is being promoted
	 * 
	 */ 
	@Transactional
	public Artwork promoteArtwork(Artwork artwork) {
		if (artwork == null) {
			throw new IllegalArgumentException("Artwork doesn't exist");
		}
		// Promote and save the artwork
		artwork.setIsBeingPromoted(true);
		artworkRepository.save(artwork);
		return artwork;
	}

	/**
	 * 
	 * Method that unpromotes a given artwork.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param Artwork Artwork to unpromote
	 * @return Artwork Artwork that is being unpromoted
	 * 
	 */ 
	@Transactional
	public Artwork unpromoteArtwork(Artwork artwork) {
		if (artwork == null) {
			throw new IllegalArgumentException("Artwork doesn't exist");
		}
		// Unpromote and save the artwork
		artwork.setIsBeingPromoted(false);
		artworkRepository.save(artwork);
		return artwork;
	}

	/**
	 * 
	 * Method that returns a list of all promoted artworks in the artwork repository.
	 * 
	 * @author OliverStappas 
	 * 
	 * @return List<Artwork> A list of artworks being promoted
	 * 
	 */ 
	@Transactional
	public List<Artwork> getAllPromotedArtworks() {
		return artworkRepository.findArtworkByIsBeingPromoted(true);
	}
	

	/**
	 * 
	 * Method that searches a list of artists given a search input and returns
	 * a hashSet of matching results. Matches the artist name with the search input.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param artists A list of artists to search in
	 * @param searchInput Text that a user searches for
	 * 
	 * @return HashSet<Artist> Artists with names matching search criteria
	 * 
	 */ 
	@Transactional
	public HashSet<Artist> searchArtist(List<Artist> artists, String searchInput) {
		if (artists == null) { // if the provided listings list is null
			throw new IllegalArgumentException("Artists must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.trim().length() == 0) { // if the searchInput is empty
			throw new IllegalArgumentException("Search cannot be empty");
		}
		HashSet<Artist> results = new HashSet<>(); // where our search results will be saved
		// Iterate through given list of listings and their corresponding artists and check if names match
		for (Artist artist : artists) {
			if (artist.getUsername().toLowerCase().replaceAll("\\s+", "")
					.contains(searchInput.toLowerCase().replaceAll("\\s+", ""))) {
				results.add(artist);
			}
		}
		return results;
	}

	/**
	 * 
	 * Method that searches a list of listings given a search input, a price range, and
	 * a given ArStyle and returns a HashSet of matching results.
	 * Matches the listing's artwork name with the search input. The artwork must be in the 
	 * price range, and have the same ArtStyle as the provided ArtStyle.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param listings A list of listings to search in
	 * @param searchInput Text that a user searches for
	 * @param minPrice The minimum price the listings should have
	 * @param maxPrice The maximum price the listings should have
	 * @param artStyle The ArtStyle the listings should have
	 * 
	 * @return HashSet<Listings> Listings with artwork names matching search criteria,
	 *	with prices within the provided range, and matching the provided ArtStyle.
	 * 
	 */ 
	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput, double minPrice, double maxPrice,
			ArtStyle artStyle) {
		if (listings == null) { // if the provided listings list is null
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.trim().length() == 0) { // if the searchInput is empty
			throw new IllegalArgumentException("Search cannot be empty");
		}
		if (minPrice > maxPrice) { // if the minimum price is larger than the maximum price
			throw new IllegalArgumentException("Min price cannot be larger than max price");
		}
		if (artStyle == null) { // if the provided ArtStyle is null
			throw new IllegalArgumentException("ArtStyle cannot be null");
		}
		HashSet<Listing> results = new HashSet<>(); // where our results will be saved
		// Iterate through given list of listings and their corresponding artworks and check if the criteria match
		for (Listing listing : listings) {
			if (!listing.isIsSold()) { // only show artworks for sale
				Artwork artwork = listing.getArtwork();
				if (artwork.getName().toLowerCase().replaceAll("\\s+", "")
						.contains(searchInput.toLowerCase().replaceAll("\\s+", "")) && artwork.getPrice() >= minPrice
						&& artwork.getPrice() <= maxPrice && artwork.getStyle().equals(artStyle)) {
					results.add(listing);
				}
			}
		}
		return results;
	}

	/**
	 * 
	 * Method that searches a list of listings given a search input, and a price range, 
	 * and returns a HashSet of matching results. Matches the listing's artwork name with 
	 * the search input. The artwork must be in the price range.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param listings A list of listings to search in
	 * @param searchInput Text that a user searches for
	 * @param minPrice The minimum price the listings should have
	 * @param maxPrice The maximum price the listings should have
	 * 
	 * @return HashSet<Listings> Listings with artwork names matching search criteria,
	 *	with prices within the provided range.
	 * 
	 */ 
	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput, double minPrice,
			double maxPrice) {
		if (listings == null) { // if the provided listings list is null
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.trim().length() == 0) { // if the searchInput is empty
			throw new IllegalArgumentException("Search cannot be empty");
		}
		if (minPrice > maxPrice) { // if the minimum price is larger than the maximum price
			throw new IllegalArgumentException("Min price cannot be larger than max price");
		}
		HashSet<Listing> results = new HashSet<>(); // where our results will be saved
		// Iterate through given list of listings and their corresponding artworks and check if the criteria match
		for (Listing listing : listings) {
			if (!listing.isIsSold()) { // only show artworks for sale
				Artwork artwork = listing.getArtwork();
				if (artwork.getName().toLowerCase().replaceAll("\\s+", "")
						.contains(searchInput.toLowerCase().replaceAll("\\s+", "")) && artwork.getPrice() >= minPrice
						&& artwork.getPrice() <= maxPrice) {
					results.add(listing);
				}
			}
		}
		return results;
	}
   
	/**
	 * 
	 * Method that searches a list of listings given a search input and
	 * a given ArStyle and returns a HashSet of matching results.
	 * Matches the listing's artwork name with the search input. The artwork must 
	 * have the same ArtStyle as the provided ArtStyle.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param listings A list of listings to search in
	 * @param searchInput Text that a user searches for
	 * @param artStyle The ArtStyle the listings should have
	 * 
	 * @return HashSet<Listings> Listings with artwork names matching search criteria 
	 * and matching the provided ArtStyle.
	 * 
	 */ 
	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput, ArtStyle artStyle) {
		if (listings == null) { // if the provided listings list is null 
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.trim().length() == 0) { // if the searchInput is empty
			throw new IllegalArgumentException("Search cannot be empty");
		}
		if (artStyle == null) { // if the provided ArtStyle is null
			throw new IllegalArgumentException("ArtStyle cannot be null");
		}
		HashSet<Listing> results = new HashSet<>(); // where our results will be saved
		// Iterate through given list of listings and their corresponding artworks and check if the criteria match
		for (Listing listing : listings) {
			if (!listing.isIsSold()) { // only show artworks for sale
				Artwork artwork = listing.getArtwork();
				if (artwork.getName().toLowerCase().replaceAll("\\s+", "").contains(
						searchInput.toLowerCase().replaceAll("\\s+", "")) && artwork.getStyle().equals(artStyle)) {
					results.add(listing);
				}
			}
		}
		return results;
	}
	
	/**
	 * 
	 * Method that searches a list of listings given a search input 
	 * and returns a HashSet of matching results. Matches the listing's 
	 * artwork name with the search input.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param listings A list of listings to search in
	 * @param searchInput Text that a user searches for
	 * 
	 * @return HashSet<Listings> Listings with artwork names matching search criteria.
	 * 
	 */ 
	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput) {
		if (listings == null) { // if the provided listings list is null
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.trim().length() == 0) { // if the searchInput is empty
			throw new IllegalArgumentException("Search cannot be empty");
		}
		HashSet<Listing> results = new HashSet<>(); // where our results will be saved
		// Iterate through given list of listings and their corresponding artworks and check if the criteria match
		for (Listing listing : listings) {
			if (!listing.isIsSold()) { // only show artworks for sale
				Artwork artwork = listing.getArtwork();
				if (artwork.getName().toLowerCase().replaceAll("\\s+", "")
						.contains(searchInput.toLowerCase().replaceAll("\\s+", ""))) {
					results.add(listing);
				}
			}
		}
		return results;
	}
	
	
	/**
	 * 
	 * Method that adds a given artwork to a given customer's browseHistory
	 * 
	 * @author OliverStappas 
	 * 
	 * @param profile The customer whose browse history is being changed
	 * @param artwork The artwork being added to the browse history
	 * 
	 * @return Set<Artwork> The customer's browseHistory
	 * 
	 */ 
	@Transactional
	public Set<Artwork> addToBrowseHistory(Profile profile, Artwork artwork) {
		if (profile == null) { // if no customer was provided
			throw new IllegalArgumentException("Customer doesn't exist");
		}
		if (artwork == null) { // if no artwork was provided
			throw new IllegalArgumentException("Artwork doesn't exist");
		}
		if (profile.getArtworksViewed() == null) { // If the customer hasn't viewed anything yet
			// Create the HashSet for the customer's browsing history
			HashSet<Artwork> viewedArtworks = new HashSet<Artwork>();
			viewedArtworks.add(artwork);
			profile.setArtworksViewed(viewedArtworks);
		}
		Set<Artwork> viewedArtworks = profile.getArtworksViewed();
		if (viewedArtworks.contains(artwork)) { // if that artwork was already in browsing history
			viewedArtworks.remove(artwork); // put it back in front
		}
		
		viewedArtworks.add(artwork);
		profile.setArtworksViewed(viewedArtworks);
		
		if(profile instanceof Customer) {
			customerRepository.save((Customer)profile);
		}else {
			artistRepository.save((Artist) profile);
		}
		
		
		return viewedArtworks;
	}

	/**
	 * 
	 * Method that returns a set of Artworks corresponding to a given customer's browseHistory
	 * 
	 * @author OliverStappas 
	 * 
	 * @param customer The customer whose browse history is being viewed
	 * 
	 * @return Set<Artwork> The customer's browseHistory
	 * 
	 */
	@Transactional
	public Set<Artwork> viewBrowsingHistory(Profile profile) {
		if (profile== null) { // if no customer was provided
			throw new IllegalArgumentException("Customer doesn't exist");
		}
		if (profile.getArtworksViewed() == null) {  // If the customer hasn't viewed anything yet
			// Create the HashSet for the customer's browsing history
			HashSet<Artwork> viewedArtworks = new HashSet<Artwork>();
			profile.setArtworksViewed(viewedArtworks);
			return profile.getArtworksViewed();
		}
		
		if(profile instanceof Customer) {
			customerRepository.save((Customer)profile);
		}else {
			artistRepository.save((Artist) profile);
		}
		
		return profile.getArtworksViewed();
	}

	/**
	 * 
	 * Helper method to retrieve lists of objects.
	 * 
	 * @author ZachS 
	 * 
	 * @param iterable Iterable list allowing us to perform an enhanced for loop.
	 * 
	 * @return List<T> A list of results
	 * 
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
