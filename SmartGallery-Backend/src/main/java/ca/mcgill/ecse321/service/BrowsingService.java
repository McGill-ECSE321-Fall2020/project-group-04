package ca.mcgill.ecse321.service;

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
		SmartGallery smartGallery = new SmartGallery();
		smartGallery.setSmartGalleryID(smartGalleryID);
		smartGalleryRepository.save(smartGallery);
		return smartGallery;
	}
	
	@Transactional
	public List<SmartGallery> getAllSmartGalleries() {
		return toList(smartGalleryRepository.findAll());
	}
	
	@Transactional 
	public Gallery createGallery(String galleryName, SmartGallery smartGallery, double commission) {
		Gallery gallery = new Gallery();
		gallery.setGalleryName(galleryName);
		gallery.setSmartGallery(smartGallery);
		gallery.setComissionPercentage(commission);
		galleryRepository.save(gallery);
		return gallery;
	}
	
	@Transactional
	public List<Gallery> getAllGalleries() {
		return toList(galleryRepository.findAll());
	}
	
	@Transactional
	public void promoteArtwork(Artwork artwork) {
		artwork.setIsBeingPromoted(true);
	}
	
	@Transactional
	public HashSet<Artist> searchArtist(SmartGallery smartGallery, String searchInput) {
		HashSet<Artist> results = new HashSet<>();
		for (Profile profile : smartGallery.getProfile()) {
			if (profile instanceof Artist && profile.getUsername().toLowerCase()
					.replaceAll("\\s+","").contains(searchInput.toLowerCase().replaceAll("\\s+",""))) {
				results.add((Artist) profile);
			}
		}
		return results;
	}
	
	@Transactional
	public HashSet<Listing> searchArtwork(Gallery gallery, String searchInput, double minPrice, double maxPrice, ArtStyle artStyle) {
		HashSet<Listing> results = new HashSet<>();
		for (Listing listing : gallery.getListing()) {
			Artwork artwork = listing.getArtwork();
			if (artwork.getName().toLowerCase().replaceAll("\\s+","").contains
					(searchInput.toLowerCase().replaceAll("\\s+","")) && artwork.getPrice() >= minPrice 
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
			if (artwork.getName().toLowerCase().replaceAll("\\s+","").contains
					(searchInput.toLowerCase().replaceAll("\\s+","")) && artwork.getPrice() >= minPrice 
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
			if (artwork.getName().toLowerCase().replaceAll("\\s+","").contains
					(searchInput.toLowerCase().replaceAll("\\s+","")) && artwork.getStyle().equals(artStyle)) {
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
			if (artwork.getName().toLowerCase().replaceAll("\\s+","").contains
					(searchInput.toLowerCase().replaceAll("\\s+",""))) {
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
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
