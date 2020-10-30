package ca.mcgill.ecse321.smartgallery.service;
 import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.smartgallery.dao.*;

@Service
public class ListingService {

	@Autowired
	ListingRepository listingRepository;
	@Autowired
	ArtworkRepository artworkRepository;
	
	/**
	 * @author Stavros Mitsoglou
	 * The following parameters are those concerning an Artwork
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
	 * This method creates and saves an Artwork.
	 */
	@Transactional
	public Artwork createArtwork(String name, int year, double price, boolean isBeingPromoted, ArtStyle style, int height,
			int weight, int width, Set<Artist> artists, Gallery gallery)
	{
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
		artwork.setArtworkID(artists.iterator().next().getUsername().hashCode() * artwork.getName().hashCode());
		artworkRepository.save(artwork);
		return artwork;
	}
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
	public Listing createListing(Artwork artwork, Date dateListed, double price, 
			Gallery gallery)
	{
		
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
	 * @param listing Listing where the artwork details are being updated
	 * the following parameters are some of the artwork fields that can be updated
	 * @param name 
	 * @param year 
	 * @param price
	 * @param style
	 * @param height
	 * @param width
	 * @param weight
	 * @return
	 * This method will update the above characteristics of the artwork in question. 
	 * Note that the artist, gallery, and artwork ID should not change.
	 */
	
	@Transactional
	public Artwork updateArtworkListed(Listing listing, String name, int year, double price, ArtStyle style, int height, int width, int weight)
	{
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
	 * @return
	 * Method will return true if there is a duplicate listing
	 */
	public boolean foundListing(Listing listing)
	{ 
		boolean found = true;
		Listing l = getListing(listing.getListingID());
		if(l == null)
			found = false;
		
		return found;
	}
	
	/**
	 * @author Stavros Mitsoglou
	 * @param listingID
	 * @return listing corresponding to the listing id
	 * 
	 * This method returns a listing from its id.
	 */
	@Transactional 
	public Listing getListing(int listingID){
		
		return listingRepository.findListingByListingID(listingID);
	}
	
	/**
	 * @author Stavros Mitsoglou
	 * @param artworkID 
	 * @return
	 * This method returns an artwork from its id.
	 */
	@Transactional 
	public Artwork getArtwork(int artworkID) {
		return artworkRepository.findArtworkByArtworkID(artworkID);
	}
	
	/**
	 * @author Stavros Mitsoglou
	 * @return all listings
	 * Return all listings in repository.
	 */
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
