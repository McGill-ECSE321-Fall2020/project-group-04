package ca.mcgill.ecse321.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.service.BrowsingService;
import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.dto.GalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.SmartGalleryDTO;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;

@CrossOrigin(origins = "*")
@RestController
public class BrowsingController {

	@Autowired
	private static ArtistRepository artistRepository;
	@Autowired
	private static ArtworkRepository artworkRepository;
	@Autowired
	private static CustomerRepository customerRepository;
	@Autowired
	private static GalleryRepository galleryRepository;
	@Autowired
	private static ListingRepository listingRepository;
	@Autowired
	private static SmartGalleryRepository smartGalleryRepository;
	@Autowired
	private static TransactionRepository transactionRepository;
	
	@Autowired
	private BrowsingService browsingService;

	@PostMapping(value = { "/smartGallery/{smartGalleryID}", "/smartGallery/{smartGalleryID}/" })
	public SmartGalleryDTO createSmartGallery(@PathVariable("smartGalleryID") int smartGalleryID)
			throws IllegalArgumentException {
		SmartGallery smartGallery = browsingService.createSmartGallery(smartGalleryID);
		return Converters.convertToDto(smartGallery);
	}

	@GetMapping(value = { "/smartGallery", "/smartGallery/" })
	public List<SmartGalleryDTO> getAllSmartGalleries() {
		List<SmartGalleryDTO> smartGalleryDTOs = new ArrayList<>();
		for (SmartGallery smartGallery : browsingService.getAllSmartGalleries()) {
			smartGalleryDTOs.add(Converters.convertToDto(smartGallery));
		}
		return smartGalleryDTOs;
	}
	
	@PostMapping(value = {"/gallery/{smartGalleryID}/{galleryName}/{commission}",
			"/gallery/{smartGalleryID}/{galleryName}/{commission}/" })
	public GalleryDTO createGallery(@PathVariable("smartGalleryID") int smartGalleryID,
			@PathVariable("galleryName") String galleryName, @PathVariable("commission") int commission)
			throws IllegalArgumentException {
		SmartGallery smartGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID);
		Gallery gallery = browsingService.createGallery(galleryName, smartGallery, commission);
		return Converters.convertToDto(gallery);
	}
	
	@GetMapping(value = { "/gallery", "/gallery/"})
	public List<GalleryDTO> getAllGalleries() {
		List<GalleryDTO> galleryDTOs = new ArrayList<>();
		for(Gallery gallery : browsingService.getAllGalleries()) {
			galleryDTOs.add(Converters.convertToDto(gallery));
		}
		return galleryDTOs;
	}

}
