package ca.mcgill.ecse321.listing.service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.smartgallery.dao.*;

public class ListingService {

	@Autowired
	ListingRepository listingRepository;
	
	/**
	 * @author Stavros Mitsoglou
	 * @param artwork Artwork related to the listing
	 * @param dateListed listed date 
	 * @param gallery gallery related to the artwork
	 * @return listing created
	 * 
	 * This method creates an initial listing that is currently for sale (not sold). No transaction has been done as of yet. 
	 * the listing id is created using the hashcode of the artwork name and the username of the artist (to make it unique)
	 */
	@Transactional
	public Listing createListing(Artwork artwork, Date dateListed, 
			Gallery gallery)
	{
		Listing listing = new Listing();
		listing.setListingID(artwork.getName().hashCode() * artwork.getArtists().iterator().next().getUsername().hashCode());
		listing.setIsSold(false);
		listing.setArtwork(artwork);
		listing.setListedDate(dateListed);
		listing.setGallery(gallery);
		listingRepository.save(listing);
		
		return listing;
		
	}
	
	@Transactional 
	public Listing getListing(int listingID){
		
		return listingRepository.findListingByListingID(listingID);
	}
	
	@Transactional 
	
	public List<Listing> getAllListings()
	{
		return toList(listingRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
