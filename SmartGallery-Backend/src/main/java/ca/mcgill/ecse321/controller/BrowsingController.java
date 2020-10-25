package ca.mcgill.ecse321.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.service.BrowsingService;
import ca.mcgill.ecse321.smartgallery.dto.ArtistDTO;
import ca.mcgill.ecse321.smartgallery.dto.ArtworkDTO;
import ca.mcgill.ecse321.smartgallery.dto.GalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.SmartGalleryDTO;
import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;

@CrossOrigin(origins = "*")
@RestController
public class BrowsingController {
	
	@Autowired
	private BrowsingService browsingService;
	
	@PostMapping(value = { "/smartGallery/{smartGalleryID}", "/smartGallery/{smartGalleryID}/" } )
	public SmartGalleryDTO createEvent(@PathVariable("smartGalleryID") int smartGalleryID)
	throws IllegalArgumentException {
		SmartGallery smartGallery = browsingService.createSmartGallery(smartGalleryID);
		return convertToDto(smartGallery);
	}
	
	@GetMapping(value = { "/smartGallery", "/smartGallery/" } )
	public List<SmartGalleryDTO> getAllSmartGalleries() {
		List<SmartGalleryDTO> smartGalleryDTOs = new ArrayList<>();
		for (SmartGallery smartGallery : browsingService.getAllSmartGalleries()) {
			smartGalleryDTOs.add(convertToDto(smartGallery));
		}
		return smartGalleryDTOs;
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
}
