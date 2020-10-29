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
		return listingService.getAllListings().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@PostMapping(value = { "/listing/{listingID}", "/listing/{ListingID}/" })
	public ListingDTO createListing(@RequestParam("artwork") Artwork artwork,
			@PathVariable("dateListed") Date dateListed, @PathVariable("price") double price,
			@RequestParam(name = "gallery") Gallery gallery) throws IllegalArgumentException {
		Listing listing = listingService.createListing(artwork, dateListed, price, gallery);
		return Converters.convertToDto(listing);
	}

}
