package ca.mcgill.ecse321.smartgallery.service;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.smartgallery.dao.*;
import ca.mcgill.ecse321.smartgallery.model.*;

@Service
public class BrowsingService {

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

	@Transactional
	public SmartGallery createSmartGallery(int smartGalleryID) {
		// Checking if ID exists already
		if (smartGalleryID == 0) {
			throw new IllegalArgumentException("SmartGallery ID must not be zero");
		}
		if (validateSmartGalleryID(smartGalleryID)) {
			throw new IllegalArgumentException("A smartGallery with that ID already exists");
		}

		SmartGallery smartGallery = new SmartGallery();
		smartGallery.setSmartGalleryID(smartGalleryID);
		smartGalleryRepository.save(smartGallery);
		return smartGallery;
	}
	
	private boolean validateSmartGalleryID (int smartGalleryID) {
		if (smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID) == null) {
			return false;
		}
		return true;
	}

	
	@Transactional
	public List<SmartGallery> getAllSmartGalleries() {
		return toList(smartGalleryRepository.findAll());
	}

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

	@Transactional
	public Gallery createGallery(String galleryName, SmartGallery smartGallery, double commission) {
		// Checking if ID exists already
		if (validateGalleryName(galleryName)) {
			throw new IllegalArgumentException("A gallery with that name already exists");
		}
		if (galleryName == null || galleryName == "" || galleryName.isBlank()) {
			throw new IllegalArgumentException("Gallery Name cannot be empty");
		}
		if (commission < 0 || commission > 100) {
			throw new IllegalArgumentException("The commission percentage must be between 0 and 100 inclusive");
		}
		if (smartGallery == null) {
			throw new IllegalArgumentException("A SmartGallery is need to create a Gallery");
		}
		Gallery gallery = new Gallery();
		gallery.setGalleryName(galleryName);
		gallery.setSmartGallery(smartGallery);
		smartGallery.setGallery(gallery);
		smartGalleryRepository.save(smartGallery);
		gallery.setComissionPercentage(commission);
		galleryRepository.save(gallery);
		return gallery;
	}
	
	private boolean validateGalleryName(String galleryName) {
		if (galleryRepository.findGalleryByGalleryName(galleryName) == null) {
			return false;
		}
		return true;
	}

	@Transactional
	public List<Gallery> getAllGalleries() {
		return toList(galleryRepository.findAll());
	}
	
	@Transactional
	public Gallery getGalleryByName(String galleryName) {
		if (galleryName == null || galleryName == "" || galleryName.isBlank()) {
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
	
	@Transactional
	public Artwork promoteArtwork(Artwork artwork) {
		if (artwork == null) {
			throw new IllegalArgumentException("Artwork doesn't exist");
		}
		artwork.setIsBeingPromoted(true);
		artworkRepository.save(artwork);
		return artwork;
	}

	@Transactional
	public Artwork unpromoteArtwork(Artwork artwork) {
		if (artwork == null) {
			throw new IllegalArgumentException("Artwork doesn't exist");
		}
		artwork.setIsBeingPromoted(false);
		artworkRepository.save(artwork);
		return artwork;
	}

	@Transactional
	public List<Artwork> getAllPromotedArtworks() {
		return artworkRepository.findArtworkByIsBeingPromoted(true);
	}

	@Transactional
	public HashSet<Artist> searchArtist(List<Artist> artists, String searchInput) {
		if (artists == null) {
			throw new IllegalArgumentException("Artists must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.isBlank()) {
			throw new IllegalArgumentException("Search cannot be empty");
		}
		HashSet<Artist> results = new HashSet<>();
		for (Artist artist : artists) {
			if (artist.getUsername().toLowerCase().replaceAll("\\s+", "")
					.contains(searchInput.toLowerCase().replaceAll("\\s+", ""))) {
				results.add(artist);
			}
		}
		return results;
	}

	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput, double minPrice, double maxPrice,
			ArtStyle artStyle) {
		if (listings == null) {
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.isBlank()) {
			throw new IllegalArgumentException("Search cannot be empty");
		}
		if (minPrice > maxPrice) {
			throw new IllegalArgumentException("Min price cannot be larger than max price");
		}
		if (artStyle == null) {
			throw new IllegalArgumentException("ArtStyle cannot be null");
		}
		HashSet<Listing> results = new HashSet<>();
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

	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput, double minPrice,
			double maxPrice) {
		if (listings == null) {
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.isBlank()) {
			throw new IllegalArgumentException("Search cannot be empty");
		}
		if (minPrice > maxPrice) {
			throw new IllegalArgumentException("Min price cannot be larger than max price");
		}
		HashSet<Listing> results = new HashSet<>();
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

	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput, ArtStyle artStyle) {
		if (listings == null) {
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.isBlank()) {
			throw new IllegalArgumentException("Search cannot be empty");
		}
		if (artStyle == null) {
			throw new IllegalArgumentException("ArtStyle cannot be null");
		}
		HashSet<Listing> results = new HashSet<>();
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

	@Transactional
	public HashSet<Listing> searchArtwork(List<Listing> listings, String searchInput) {
		if (listings == null) {
			throw new IllegalArgumentException("Listings must be provided");
		}
		if (searchInput == null || searchInput == "" || searchInput.isBlank()) {
			throw new IllegalArgumentException("Search cannot be empty");
		}
		HashSet<Listing> results = new HashSet<>();
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

	@Transactional
	public Set<Artwork> addToBrowseHistory(Customer customer, Artwork artwork) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer doesn't exist");
		}
		if (artwork == null) {
			throw new IllegalArgumentException("Artwork doesn't exist");
		}
		if (customer.getArtworksViewed() == null) {
			HashSet<Artwork> viewedArtworks = new HashSet<Artwork>();
			viewedArtworks.add(artwork);
			customer.setArtworksViewed(viewedArtworks);
			return customer.getArtworksViewed();
		}
		Set<Artwork> viewedArtworks = customer.getArtworksViewed();
		if (viewedArtworks.contains(artwork)) { // if that artwork was already in browsing history
			viewedArtworks.remove(artwork); // put it back in front
		}
		viewedArtworks.add(artwork);
		return viewedArtworks;
	}

	@Transactional
	public Set<Artwork> viewBrowsingHistory(Customer customer) {
		if (customer == null) {
			throw new IllegalArgumentException("Customer doesn't exist");
		}
		if (customer.getArtworksViewed() == null) {
			HashSet<Artwork> viewedArtworks = new HashSet<Artwork>();
			customer.setArtworksViewed(viewedArtworks);
			return customer.getArtworksViewed();
		}
		return customer.getArtworksViewed();
	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
