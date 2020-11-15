package ca.mcgill.ecse321.smartgallery.service;
 import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
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
	@Autowired
	ArtistRepository artistRepository;
	@Autowired 
	GalleryRepository galleryRepository;

	
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
	public Artwork createArtwork(String name, Integer year, Double price, boolean isBeingPromoted, ArtStyle style, Integer height,
			Integer weight, Integer width, Artist artist, Gallery gallery)
	{
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error += "Artwork name must be specified";
		}

		if (price == null) {
			error += "Price must be specified";
		}

		if(year == null) {
			error += "Year must be specified";
		}
		
		if (style == null) {
			error += "Art style must be specified";
		}

		if (height == null) {
			error += "Height must be specified";
		}

		if (weight == null) {
			error += "Weight must be specified";
		}
		
		if(width == null) {
			error += "Width must be specified";
		}
		
		if (artist == null) {
			error += "Artist must exist";
			
		}
		else if(artistRepository.findArtistByUsername(artist.getUsername()) == null) {
			error += "Artist must exist";
		}
		
		if (gallery == null) {
			error += "Gallery must exist";
		}
		
		else if(galleryRepository.findGalleryByGalleryName(gallery.getGalleryName()) == null) {
			error += "Gallery must exist";
		}
		
		int potentialArtworkId=0;
		if(artist!=null && name!=null) {
			potentialArtworkId = artist.getUsername().hashCode() * name.hashCode();
		}
		if(artworkRepository.existsById(potentialArtworkId)) {
			error += "This artwork for the specified user already exists";
		}
		

		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

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
        //artwork.setImageUrl(null);
		
		Set<Artwork> artworks = artist.getArtworks();
		if(artworks == null || artworks.size() == 0)
		{
			HashSet<Artist> artists = new HashSet<>();
			HashSet<Artwork> artworkss = new HashSet<Artwork>();
			artworkss.add(artwork);
			artist.setArtworks(artworkss);
			artists.add(artist);
			artwork.setArtists(artists);
			artwork.setGallery(gallery);
			artwork.setArtworkID(artists.iterator().next().getUsername().hashCode() * artwork.getName().hashCode());
			artworkRepository.save(artwork);
			artistRepository.save(artist);
			return artwork;
		}
		else
		{
			
			Set<Artist> artists = artist.getArtworks().iterator().next().getArtists();
			artworks.add(artwork);
			artist.setArtworks(artworks);
			artists.add(artist);
			artwork.setArtists(artists);
			artwork.setGallery(gallery);
			artwork.setArtworkID(artists.iterator().next().getUsername().hashCode() * artwork.getName().hashCode());
			artworkRepository.save(artwork);
			artistRepository.save(artist);
			return artwork;
		}
		
	}
	
	/**
	 * Set the imageUrl of an Artwork
	 * @param artwork The artwork object
	 * @param imageUrl The url of the image
	 */
	public void setArtworkImageUrl(Artwork artwork, String imageUrl) {
	  artwork.setImageUrl(imageUrl);
	  artworkRepository.save(artwork);
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
	public Listing createListing(Artwork artwork, Date dateListed, Double price, 
			Gallery gallery)
	{
		String error = "";

		if (artwork == null) {
			error += "Artwork must exist";
		}
		else if(artworkRepository.findArtworkByArtworkID(artwork.getArtworkID()) == null) {
			error += "Artwork must exist";
		}

		if (dateListed == null) {
			error += "Listed date must be specified";
		}

		if (price == null) {
			error += "Price must be specified";
		}

		if (gallery==null) {
			error += "Gallery must exist";
		}
		else if(galleryRepository.findGalleryByGalleryName(gallery.getGalleryName()) == null) {
			error += "Gallery must exist";
		}


		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		Listing listing = new Listing();
		listing.setListingID(artwork.getArtworkID() * artwork.getArtists().iterator().next().getUsername().hashCode());
		listing.setIsSold(false);
		listing.setArtwork(artwork);
		listing.setListedDate(dateListed);
		listing.setGallery(gallery);
		artwork.setListing(listing);
		artwork.setPrice(price);
		listingRepository.save(listing);
		artworkRepository.save(artwork);
		return listing;
		
	}
	
	/**
	 * 
	 * @param artist Artist to be added to artwork
	 * @param artworkID 
	 * @return the updated set of artitsts for an artwork
	 * 
	 * Add artist to an existing artwork
	 */
	
	@Transactional
	public Artwork addArtist(Artist artist, Integer artworkID) {
		
		String error = "";

		if (artist == null) {
			error += "Artist must exist";
		}
		else if(artistRepository.findArtistByUsername(artist.getUsername()) == null) {
			error += "Artist must exist";
		}

		if (artworkRepository.findArtworkByArtworkID(artworkID) == null) {
			error += "Artwork must exist";
		}

		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkID);
		Artist artistToAdd = artistRepository.findArtistByUsername(artist.getUsername());
		Set<Artist> artists = artwork.getArtists();
		artists.add(artist);
		artwork.setArtists(artists);
		Set<Artwork> artworks = artistToAdd.getArtworks();
		artworks.add(artwork);
		artistToAdd.setArtworks(artworks);
		artistRepository.save(artistToAdd);
		artworkRepository.save(artwork);
		return artwork;
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
	public Artwork updateArtworkListed(Listing listing, String name, Integer year, Double price, ArtStyle style, Integer height, Integer width, Integer weight)
	{
		
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error += "Artwork name must be specified";
		}

		if (listing == null) {
			error += "Listing must exist";
		}
		
		else if(listingRepository.findListingByListingID(listing.getListingID()) == null) {
			error += "Listing must exist";
		}

		if (year == null) {
			error += "Year must be specified";
		}

		if (price == null) {
			error += "Price must be specified";
		}

		if (style == null) {
			error += "Art style must be specified";
		}

		if (height == null) {
			error += "Height must be specified";
		}

		if (weight == null) {
			error += "Weight must be specified";
		}
		
		if(width == null) {
			error += "Width must be specified";
		}
		
	

		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
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
	public Artwork deleteListingAndArtwork(Listing listing) {
		
	
		String error = "";
		if(listing == null) {
			error += "Listing must exist";
		}
		
		else if(listingRepository.findListingByListingID(listing.getListingID()) == null)
		{
			error += "Listing must exist";
		}
	
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}
		
		
		Artwork artwork = listing.getArtwork();
		Transaction transaction = listing.getTransaction();
		
		Set<Artist> artists = artwork.getArtists();
		for(Artist a : artists) {
			Set<Artwork> artworks = a.getArtworks();
			artworks.remove(artwork);
			a.setArtworks(artworks);
			artistRepository.save(a);
		}
		
		if(transaction==null)
		{
			listing.setArtwork(null);
			artwork.setListing(null);
			listingRepository.delete(listing);
			artworkRepository.delete(artwork);
			
		}
		
		return artwork;
	}
	

	
	/**
	 * @author Stavros Mitsoglou
	 * @param listingID
	 * @return listing corresponding to the listing id
	 * 
	 * This method returns a listing from its id.
	 */
	@Transactional 
	public Listing getListingByID(int listingID){
		
		return listingRepository.findListingByListingID(listingID);
	}
	

	
	/**
	 * @author Stavros Mitsoglou
	 * @param artworkID 
	 * @return
	 * This method returns an artwork from its id.
	 */
	@Transactional 
	public Artwork getArtworkByID(int artworkID) {
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
	

	/**
	 * @author Stavros Mitsoglou
	 * @return all artworks
	 * Return all artworks in Repository 
	 */
	public List<Artwork> getAllArtworks()
	{
		return toList(artworkRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
}
