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
import ca.mcgill.ecse321.smartgallery.dto.SmartGalleryDTO;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;

@CrossOrigin(origins = "*")
@RestController
public class BrowsingController {

	@Autowired
	private BrowsingService browsingService;

	@PostMapping(value = { "/smartGallery/{smartGalleryID}", "/smartGallery/{smartGalleryID}/" })
	public SmartGalleryDTO createEvent(@PathVariable("smartGalleryID") int smartGalleryID)
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

}
