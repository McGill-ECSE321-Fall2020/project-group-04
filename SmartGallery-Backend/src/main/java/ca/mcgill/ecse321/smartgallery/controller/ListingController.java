package ca.mcgill.ecse321.smartgallery.controller;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.smartgallery.dto.*;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.smartgallery.service.ListingService;
import ca.mcgill.ecse321.smartgallery.dao.*;
//import ca.mcgill.ecse321.controller.*;
//import ca.mcgill.ecse321.controller.*;

@CrossOrigin(origins = "*")
@RestController
public class ListingController {

	@Autowired
	private ListingService listingService;


	@Autowired
	private ArtworkRepository artworkRepository;
	@Autowired
	private GalleryRepository galleryRepository;
	

	@GetMapping(value = { "/listing", "/listing/" })
	public List<ListingDTO> getAllListings() {
		return listingService.getAllListings().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}


	@PostMapping(value = { "/listing/{listingID}", "/listing/listingID}/" })
	public ListingDTO createListing(@PathVariable("artwork") int artworkID,
			@PathVariable("dateListed") Date dateListed, @PathVariable("price") double price,
			@PathVariable("gallery") String galleryName) throws IllegalArgumentException {
		Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkID);
		//ArtworkDTO artworkDto = Converters.convertToDto(artwork);
		Gallery gallery = galleryRepository.findGalleryByGalleryName(galleryName);
		//GalleryDTO galleryDto = Converters.convertToDto(gallery);
		Listing listing = listingService.createListing(artwork, dateListed, price, gallery);
		return Converters.convertToDto(listing);
	}

}
