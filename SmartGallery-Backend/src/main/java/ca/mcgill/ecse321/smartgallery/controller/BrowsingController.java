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
	private SmartGalleryRepository smartGalleryRepository;

	@Autowired
	private BrowsingService browsingService;

	@Autowired
	private ListingService listingService;

	@Autowired
	private RegistrationService registrationService;

	
	/**
	 * 
	 * Method that creates a SmartGallery given a SmartGalleryID.
	 * 
	 * @author ZachS
	 * 
	 * @param smartGalleryID ID for the SmartGallery
	 * @return SmartGallery The SmartGallery created with the ID
	 * @throws IllegalArgumentException
	 */
	@PostMapping(value = { "/smartGallery/{smartGalleryID}", "/smartGallery/{smartGalleryID}/" })
	public SmartGalleryDTO createSmartGallery(@PathVariable("smartGalleryID") int smartGalleryID)
			throws IllegalArgumentException {
		SmartGallery smartGallery = browsingService.createSmartGallery(smartGalleryID);
		return Converters.convertToDto(smartGallery);
	}

	/**
	 * 
	 * Method that returns a list of all the SmartGalleryDTOs in the smartGallery repository.
	 * 
	 * @author ZachS 
	 * 
	 * @return A list of all SmartGalleries
	 */
	@GetMapping(value = { "/smartGallery", "/smartGallery/" })
	public List<SmartGalleryDTO> getAllSmartGalleries() {
		List<SmartGalleryDTO> smartGalleryDTOs = new ArrayList<>();
		for (SmartGallery smartGallery : browsingService.getAllSmartGalleries()) {
			smartGalleryDTOs.add(Converters.convertToDto(smartGallery));
		}
		return smartGalleryDTOs;
	}

	/**
	 * 
	 * Method that creates a Gallery given a galleryName, commission percentage
	 * and SmartGalleryID.
	 * 
	 * @author ZachS
	 * 
	 * @param galleryName name for the Gallery
	 * @param commission  percentage of commission the gallery receives on transactions
	 * @param smartGalleryID the ID of the SmartGallery the gallery will be contained in

	 * @return Gallery The GalleryDTO created with the provided parameters
	 * 
	 * @throws IllegalArgumentException
	 * 
	 */
	@PostMapping(value = { "/gallery/{smartGalleryID}/{galleryName}/{commission}",
			"/gallery/{smartGalleryID}/{galleryName}/{commission}/" })
	public GalleryDTO createGallery(@PathVariable("smartGalleryID") int smartGalleryID,
			@PathVariable("galleryName") String galleryName, @PathVariable("commission") int commission)
			throws IllegalArgumentException {
		SmartGallery smartGallery = smartGalleryRepository.findSmartGalleryBySmartGalleryID(smartGalleryID);
		Gallery gallery = browsingService.createGallery(galleryName, smartGallery, commission);
		return Converters.convertToDto(gallery);
	}

	/**
	 * 
	 * Method that returns a list of all the GalleryDTOs in the gallery repository.
	 * 
	 * @author ZachS 
	 * 
	 * @return A list of all GalleryDTOs
	 * 
	 */
	@GetMapping(value = { "/gallery", "/gallery/" })
	public List<GalleryDTO> getAllGalleries() {
		List<GalleryDTO> galleryDTOs = new ArrayList<>();
		for (Gallery gallery : browsingService.getAllGalleries()) {
			galleryDTOs.add(Converters.convertToDto(gallery));
		}
		return galleryDTOs;
	}

	/**
	 * 
	 * Method that searches all listings given a search input, a price range and
	 * a given ArStyle and returns a HashSet of matching results.
	 * Matches the listing's artwork name with the search input. The artwork must be in the 
	 * price range, and have the same ArtStyle as the provided ArtStyle.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param searchInput Text that a user searches for
	 * @param minPrice The minimum price the listings should have
	 * @param maxPrice The maximum price the listings should have
	 * @param artStyle The ArtStyle the listings should have
	 * 
	 * @return HashSet<ListingDTO> ListingDTOSs with artwork names matching search criteria,
	 *	with prices within the provided range, and matching the provided ArtStyle.
	 * 
	 */ 
	@GetMapping(value = { "/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/{style}",
			"/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/{style}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput, 
			@PathVariable("minPrice") double minPrice, @PathVariable("maxPrice") double maxPrice, 
			@PathVariable("style") String artStyle) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput, minPrice, maxPrice, 
				Converters.convertStringToArtStyle(artStyle)).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * Method that searches all listings given a search input, and a price range, 
	 * and returns a HashSet of matching results. Matches the listing's artwork name with 
	 * the search input. The artwork must be in the price range.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param searchInput Text that a user searches for
	 * @param minPrice The minimum price the listings should have
	 * @param maxPrice The maximum price the listings should have
	 * 
	 * @return HashSet<ListingDTO> ListingDTOS with artwork names matching search criteria,
	 *	with prices within the provided range.
	 * 
	 */ 
	@GetMapping(value = { "/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}",
			"/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput,
			@PathVariable("minPrice") double minPrice, @PathVariable("maxPrice") double maxPrice) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput, minPrice, maxPrice).stream()
				.map(p -> Converters.convertToDto(p)).collect(Collectors.toList());
	}

	/**
	 * 
	 * Method that searches all listings given a search input and
	 * a given ArStyle and returns a HashSet of matching results.
	 * Matches the listing's artwork name with the search input. The artwork must 
	 * have the same ArtStyle as the provided ArtStyle.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param searchInput Text that a user searches for
	 * @param artStyle The ArtStyle the listings should have
	 * 
	 * @return HashSet<ListingDTO> ListingDTOS with artwork names matching search criteria 
	 * and matching the provided ArtStyle.
	 * 
	 */ 
	@GetMapping(value = { "/listing/artworkSearch/{searchInput}/{style}",
			"/listing/artworkSearch/{searchInput}/{style}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput, 
			@PathVariable("style") String artStyle) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput, 
				Converters.convertStringToArtStyle(artStyle)).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * Method that searches all listings given a search input 
	 * and returns a hashSet of matching results. Matches the listing's 
	 * artwork name with the search input.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param searchInput Text that a user searches for
	 * 
	 * @return HashSet<ListingDTO> ListingDTOS with artwork names matching search criteria.
	 * 
	 */ 
	@GetMapping(value = { "/listing/artworkSearch/{searchInput}", "/listing/artworkSearch/{searchInput}/" })
	public List<ListingDTO> searchArtwork(@PathVariable("searchInput") String searchInput) {
		List<Listing> allListings = listingService.getAllListings();
		return browsingService.searchArtwork(allListings, searchInput).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * Method that searches a list of artists given a search input and returns
	 * a HashSet of matching results. Matches the artist name with the search input.
	 * 
	 * @author OliverStappas 
	 * 
	 * @param searchInput Text that a user searches for
	 * 
	 * @return HashSet<ArtistDTO> ArtistDTOs with names matching search criteria
	 * 
	 */ 
	@GetMapping(value = { "/artist/artistSearch/{searchInput}", "/artist/artistSearch/{searchInput}/" })
	public List<ArtistDTO> searchArtist(@PathVariable("searchInput") String searchInput) {
		List<Artist> allArtists = registrationService.getAllArtists();
		return browsingService.searchArtist(allArtists, searchInput).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * Method that returns a list of Artworks corresponding to a given customer's browseHistory
	 * 
	 * @author OliverStappas
	 * 
	 * @param username The username of the customer whose browse history is being changed
	 * 
	 * @return List<ArtworkDTO> The customer's browseHistory
	 * 
	 */
	@GetMapping(value = { "/customer/viewBrowsingHistory/{username}", "/customer/viewBrowsingHistory/{username}/" })
	public List<ArtworkDTO> viewBrowsingHistory(@PathVariable("username") String username) {
		Customer customer = registrationService.getCustomer(username);
		return browsingService.viewBrowsingHistory(customer).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * Method that adds a given artwork to a given customer's browseHistory
	 * 
	 * @author OliverStappas 
	 * 
	 * @param username The username of the customer whose browse history is being changed
	 * @param artworkID The ID of the artwork being added to the browse history
	 * 
	 * @return List<ArtworkDTO> The customer's browseHistory
	 * 
	 */ 
	@PutMapping(value = { "/customer/addToBrowseHistory/{username}/{artworkID}",
			"/customer/addToBrowseHistory/{username}/{artworkID}/" })
	public List<ArtworkDTO> addToBrowseHistory(@PathVariable("username") String username,
			@PathVariable("artworkID") int artworkID) {
		Customer customer = registrationService.getCustomer(username);
		Artwork artwork = listingService.getArtworkByID(artworkID);
		return browsingService.addToBrowseHistory(customer, artwork).stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * Method that promotes a given artwork (from its ID).
	 * 
	 * @author OliverStappas 
	 * 
	 * @param artworkID ID of Artwork to promote
	 * @return ArtworkDTO ArtworkDTO that is being promoted
	 * 
	 */ 
	@PutMapping(value = { "/artwork/promote/{artworkID}", "/artwork/promote/{artworkID}/" })
	public ArtworkDTO promoteArtwork(@PathVariable("artworkID") int artworkID) {
		Artwork artwork = listingService.getArtworkByID(artworkID);
		return Converters.convertToDto(browsingService.promoteArtwork(artwork));
	}

	/**
	 * 
	 * Method that unpromotes a given artwork (from its ID).
	 * 
	 * @author OliverStappas 
	 * 
	 * @param artworkID ID of Artwork to unpromote
	 * @return ArtworkDTO ArtworkDTO that is being unpromoted
	 * 
	 */ 
	@PutMapping(value = { "/artwork/unpromote/{artworkID}", "/artwork/unpromote/{artworkID}/" })
	public ArtworkDTO unpromoteArtwork(@PathVariable("artworkID") int artworkID) {
		Artwork artwork = listingService.getArtworkByID(artworkID);
		return Converters.convertToDto(browsingService.unpromoteArtwork(artwork));
	}

	/**
	 * 
	 * Method that returns a list of all promoted ArtworkDTOs in the artwork repository.
	 * 
	 * @author OliverStappas 
	 * 
	 * @return List<ArtworkDTOs> A list of ArtworkDTOs being promoted
	 * 
	 */ 
	@GetMapping(value = { "/artwork/getPromoted", "/artwork/getPromoted/" })
	public List<ArtworkDTO> getAllPromotedArtworks() {
		return browsingService.getAllPromotedArtworks().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}
	
	
	/**
	 * 
	 * Method that returns a SmartGalleryDTO that has the given smartGalleryID.
	 * 
	 * @author OliverStappas
	 * 
	 * @param smartGalleryID smartGalleryID for the SmartGallery
	 * @return SmartGalleryDTO A smartGallery with the smartGalleryID provided from the smartGalleryRepository
	 * 
	 */
	@GetMapping(value = { "/smartGallery/{smartGalleryID}", "/smartGallery/{smartGalleryID}/" })
	public SmartGalleryDTO getSmartGalleryByID(@PathVariable("smartGalleryID") int smartGalleryID) {
		return Converters.convertToDto(browsingService.getSmartGalleryByID(smartGalleryID));
	}
	
	/**
	 * 
	 * Method that returns a GalleryDTO that has the given galleryName.
	 * 
	 * @author OliverStappas
	 * 
	 * @param galleryName galleryName for the Gallery
	 * @return GalleryDTO A gallery with the galleryName provided from the galleryRepository
	 * 
	 */
	@GetMapping(value = { "/gallery/{galleryName}", "/gallery/{galleryName}/" })
	public GalleryDTO getGalleryByID(@PathVariable("galleryName") String galleryName) {
		return Converters.convertToDto(browsingService.getGalleryByName(galleryName));
	}


}
