package ca.mcgill.ecse321.smartgallery.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.smartgallery.dto.*;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.smartgallery.service.ListingService;
import ca.mcgill.ecse321.smartgallery.dao.*;


@CrossOrigin(origins = "*")
@RestController
public class ListingController {


	@Autowired
	private ArtworkRepository artworkRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	@Autowired
	private ArtistRepository artistRepository;
	@Autowired
	private ListingRepository listingRepository;
	
	@Autowired
	private ListingService listingService;
	

	@GetMapping(value = { "/listing", "/listing/" })
	public List<ListingDTO> getAllListings() {
		return listingService.getAllListings().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}
	

	@GetMapping(value = { "/artwork", "/artwork/" })
	public List<ArtworkDTO> getAllArtworks() {
		return listingService.getAllArtworks().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/listing/{listingID}", "/listing/{listingID}/" })
	public ListingDTO getListingByID(@PathVariable("listingID") int listingID) {
		return Converters.convertToDto(listingService.getListingByID(listingID));
				
	}
	
	@GetMapping(value = { "/artwork/{artworkID}", "/artwork/{artworkID}/" })
	public ArtworkDTO getArtworkByID(@PathVariable("artworkID") int artworkID) {
		return Converters.convertToDto(listingService.getArtworkByID(artworkID));
	}
	
	@PostMapping(value = { "/listing/{artworkID}", "/listing/{artworkID}/" })
	public ListingDTO createListing(@PathVariable("artworkID") int artworkID,
			@RequestParam("price") double price,
			@RequestParam("gallery") String galleryName) throws IllegalArgumentException {
		Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkID);
		long millis = System.currentTimeMillis();
		Date dateListed = new java.sql.Date(millis);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(galleryName);
		Listing listing = listingService.createListing(artwork, dateListed, price, gallery);
		return Converters.convertToDto(listing);
	}
	
	@PostMapping(value = { "/artwork/{artworkName}", "artwork/{artworkName}/"})
	public ArtworkDTO createArtwork( @PathVariable("artworkName") String artworkName, @RequestParam("year")int year, 
			@RequestParam("price") double price, 
			@RequestParam("style")String style, 
			@RequestParam("height")int height,
			@RequestParam("weight")int weight, 
			@RequestParam("width")int width, 
			@RequestParam("artist") String artistName, 
			@RequestParam("gallery") String galleryName)throws IllegalArgumentException{
		
		Artist artist = artistRepository.findArtistByUsername(artistName);
		ArtStyle artStyle = Converters.convertStringToArtStyle(style);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(galleryName);
		Artwork artwork = listingService.createArtwork(artworkName, year, price, false, artStyle, height, weight, width, artist, gallery);
		
		
		return Converters.convertToDto(artwork);
		
	}
	
	@PutMapping(value = {"/artwork/setImageUrl", "/artwork/setImageUrl/"})
	public String setArtworkImageUrl(@RequestParam("artworkID") int artworkID,
	        @RequestParam("imageUrl") String imageUrl) {
	    Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkID);
	    listingService.setArtworkImageUrl(artwork, imageUrl);
	    return imageUrl;
	}
	
	@PutMapping(value = {"/artwork/addArtist/{artworkID}/{artist}", "/artwork/addArtist/{artworkID}/{artist}/"})
	public ArtworkDTO addArtistToArtwork(@PathVariable("artworkID") int artworkID, 
			@PathVariable("artist") String artistName) throws IllegalArgumentException{
		
		Artist artist = artistRepository.findArtistByUsername(artistName);
		Artwork updatedArtwork = listingService.addArtist(artist, artworkID);
		return Converters.convertToDto(updatedArtwork);
		
	}
	
	
	@PutMapping(value = {"listing/updateArtwork/{listingID}", "listing/updateArtwork/{listingID}/"})
	public ArtworkDTO updateArtwork(@PathVariable("listingID")int listingID, 
			@RequestParam("artworkName")String name, 
			@RequestParam("year")int year, 
			@RequestParam("price")double price, 
			@RequestParam("style")String style, 
			@RequestParam("height")int height, 
			@RequestParam("width")int width, 
			@RequestParam("weight")int weight) throws IllegalArgumentException{
		
		Listing listing = listingRepository.findListingByListingID(listingID);
		ArtStyle artStyle = Converters.convertStringToArtStyle(style);
		Artwork artwork = listingService.updateArtworkListed(listing, name, year, price, artStyle, height, width, weight);
		return Converters.convertToDto(artwork);

	}
	
	
	@PostMapping(value = {"listing/deleteListingAndArtwork/{listingID}", "listing/deleteListingAndArtwork/{listingID}/"})
	public ArtworkDTO deleteListingAndArtwork(@PathVariable("listingID")int listingID) throws IllegalArgumentException{
		
		Listing listing = listingRepository.findListingByListingID(listingID);
		Artwork deletedArtwork = listingService.deleteListingAndArtwork(listing);
		return Converters.convertToDto(deletedArtwork);

	}
	
	
	  
	

}
