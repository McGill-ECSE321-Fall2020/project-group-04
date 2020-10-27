package ca.mcgill.ecse321.controller;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.smartgallery.dto.*;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.service.ListingService;
//import ca.mcgill.ecse321.controller.*;

@CrossOrigin(origins = "*")
@RestController
public class ListingController {
	
	@Autowired
	private ListingService listingService;
	
	@GetMapping(value = { "/listing", "/listing/" })
	public List<ListingDTO> getAllListings() {
		return listingService.getAllListings().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
	
	@PostMapping(value = { "/listing/{listingID}", "/listing/{ListingID}/" })
	public ListingDTO createListing(@RequestParam("artwork") Artwork artwork, @PathVariable("dateListed") Date dateListed, 
		@PathVariable("price") double price, @RequestParam(name = "gallery") Gallery gallery) throws IllegalArgumentException {
		Listing listing = listingService.createListing(artwork,dateListed, price,  gallery);
		return convertToDto(listing);
	}
	
	private SmartGalleryDTO convertToDto(SmartGallery s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such SmartGallery.");
		}
		SmartGalleryDTO smartGalleryDTO = new SmartGalleryDTO(s.getSmartGalleryID());
		return smartGalleryDTO;
	}
	
	private GalleryDTO convertToDto(Gallery g) {
		if (g == null) {
			throw new IllegalArgumentException("There is no such SmartGallery.");
		}
		GalleryDTO galleryDTO = new GalleryDTO(convertToDto(g.getSmartGallery()), g.getGalleryName(),
				g.getComissionPercentage());
		return galleryDTO;
	}
	
	private ArtistDTO convertToDto(Artist a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Artist.");
		}
		ArtistDTO artistDTO = new ArtistDTO(convertToDto(a.getSmartGallery()), a.getUsername(),
				a.getPassword(), a.getEmail(), a.getDefaultPaymentMethod(), a.getCreationDate(),
				a.isIsVerified());
		return artistDTO;
	}
	
	private ArtworkDTO convertToDto(Artwork a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Artwork.");
		}
		Set<Artist> artists = a.getArtists();
		Set<ArtistDTO> artistsDTO = new HashSet<>();
		for (Artist ar : artists) {
			artistsDTO.add(convertToDto(ar));
		}
		ArtworkDTO artworkDTO = new ArtworkDTO(artistsDTO, convertToDto(a.getGallery()),
				a.getName(), a.getYear(), a.getPrice(), a.isIsBeingPromoted(), a.getStyle(),
				a.getHeight(), a.getWeight(), a.getWidth(), a.getArtworkID());
		return artworkDTO;
	}
	
	private ListingDTO convertToDto(Listing l) {
		if (l == null) {
			throw new IllegalArgumentException("There is no such Listing");
		}
		ListingDTO listingDto = new ListingDTO(convertToDto(l.getGallery()), convertToDto(l.getArtwork()), l.getListedDate(), l.isIsSold(), l.getListingID());
		return listingDto;
	}
	
	


}
