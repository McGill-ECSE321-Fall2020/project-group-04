package ca.mcgill.ecse321.smartgallery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.dto.ArtistDTO;
import ca.mcgill.ecse321.smartgallery.dto.ArtworkDTO;
import ca.mcgill.ecse321.smartgallery.dto.GalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.ListingDTO;
import ca.mcgill.ecse321.smartgallery.dto.SmartGalleryDTO;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.service.BrowsingService;
import ca.mcgill.ecse321.smartgallery.service.ListingService;
import ca.mcgill.ecse321.smartgallery.service.RegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class BrowsingController {

	@Autowired
	private  ArtistRepository artistRepository;
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

	@Autowired
	private BrowsingService browsingService;

	@Autowired
	private ListingService listingService;

	@Autowired
	private RegistrationService registrationService;

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

	@PostMapping(value = { "/gallery/{smartGalleryID}/{galleryName}/{commission}",
			"/gallery/{smartGalleryID}/{galleryName}/{commission}/" })
	public GalleryDTO createGallery(@PathVariable("smartGalleryID") int smartGalleryID,
			@PathVariable("galleryName") String galleryName, @PathVariable("commission") int commission)
			throws IllegalArgumentException {
		SmartGallery smartGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID);
		Gallery gallery = browsingService.createGallery(galleryName, smartGallery, commission);
		return Converters.convertToDto(gallery);
	}

	@GetMapping(value = { "/gallery", "/gallery/" })
	public List<GalleryDTO> getAllGalleries() {
		List<GalleryDTO> galleryDTOs = new ArrayList<>();
		for (Gallery gallery : browsingService.getAllGalleries()) {
			galleryDTOs.add(Converters.convertToDto(gallery));
		}
		return galleryDTOs;
	}

	@GetMapping(value = { "/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/{style}",
			"/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/{style}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput, 
			@PathVariable("minPrice") double minPrice, @PathVariable("minPrice") double maxPrice, 
			@PathVariable("style") String artStyle) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput, minPrice, maxPrice, 
				Converters.convertStringToArtStyle(artStyle)).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@GetMapping(value = { "/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}",
			"/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput,
			@PathVariable("minPrice") double minPrice, @PathVariable("minPrice") double maxPrice) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput, minPrice, maxPrice).stream()
				.map(p -> Converters.convertToDto(p)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/listing/artworkSearch/{searchInput}/{style}",
			"/listing/artworkSearch/{searchInput}/{style}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput, 
			@PathVariable("style") String artStyle) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput, 
				Converters.convertStringToArtStyle(artStyle)).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@GetMapping(value = { "/listing/artworkSearch/{searchInput}", "/listing/artworkSearch/{searchInput}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@GetMapping(value = { "/artist/artistSearch/{searchInput}", "/artist/artistSearch/{searchInput}/" })
	public List<ArtistDTO> searchArtist(@PathVariable("searchInput") String searchInput) {
		List<Artist> allArtists = registrationService.getAllArtists();
		return browsingService.searchArtist(allArtists, searchInput).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@GetMapping(value = { "/customer/viewBrowsingHistory/{username}", "/customer/viewBrowsingHistory/{username}/" })
	public List<ArtworkDTO> viewBrowsingHistory(@PathVariable("username") String username) {
		Customer customer = registrationService.getCustomer(username);
		return browsingService.viewBrowsingHistory(customer).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@PutMapping(value = { "/customer/addToBrowseHistory/{username}/{artworkID}",
			"/customer/addToBrowseHistory/{username}/{artworkID}/" })
	public List<ArtworkDTO> addToBrowseHistory(@PathVariable("username") String username,
			@PathVariable("artworkID") int artworkID) {
		Customer customer = registrationService.getCustomer(username);
		Artwork artwork = listingService.getArtworkByID(artworkID);
		return browsingService.addToBrowseHistory(customer, artwork).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@PutMapping(value = { "/artwork/promote/{artworkID}", "/artwork/promote/{artworkID}/" })
	public ArtworkDTO promoteArtwork(@PathVariable("artworkID") int artworkID) {
		Artwork artwork = listingService.getArtworkByID(artworkID);
		return Converters.convertToDto(browsingService.promoteArtwork(artwork));
	}

	@PutMapping(value = { "/artwork/unpromote/{artworkID}", "/artwork/unpromote/{artworkID}/" })
	public ArtworkDTO unpromoteArtwork(@PathVariable("artworkID") int artworkID) {
		Artwork artwork = listingService.getArtworkByID(artworkID);
		return Converters.convertToDto(browsingService.unpromoteArtwork(artwork));
	}

	@GetMapping(value = { "/artwork/getPromoted", "/artwork/getPromoted/" })
	public List<ArtworkDTO> getAllPromotedArtworks() {
		return browsingService.getAllPromotedArtworks().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/smartGallery/{smartGalleryID}", "/smartGallery/{smartGalleryID}/" })
	public SmartGalleryDTO getSmartGalleryByID(@PathVariable("smartGalleryID") int smartGalleryID) {
		return Converters.convertToDto(browsingService.getSmartGalleryByID(smartGalleryID));
	}
	
	@GetMapping(value = { "/gallery/{galleryName}", "/gallery/{galleryName}/" })
	public GalleryDTO getGalleryByID(@PathVariable("galleryName") String galleryName) {
		return Converters.convertToDto(browsingService.getGalleryByName(galleryName));
	}


}
