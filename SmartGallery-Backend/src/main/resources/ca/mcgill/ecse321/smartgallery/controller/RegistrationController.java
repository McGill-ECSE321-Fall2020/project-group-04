package ca.mcgill.ecse321.smartgallery.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.smartgallery.dto.ArtistDTO;
import ca.mcgill.ecse321.smartgallery.dto.CustomerDTO;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.service.BrowsingService;
import ca.mcgill.ecse321.smartgallery.service.RegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	private BrowsingService browsingService;

	@GetMapping(value = { "/customer", "/customer/" })
	public List<CustomerDTO> getAllCustomers() {
		return registrationService.getAllCustomers().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@PostMapping(value = { "/customer/{username}", "/customer/{username}/" })
	public CustomerDTO createCustomer(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("defaultPaymentMethod") String defaultPaymentMethod,
			@RequestParam("creationDate") Date creationDate) throws IllegalArgumentException {
		SmartGallery sGallery = browsingService.getAllSmartGalleries().get(0);
		Customer customer = registrationService.createCustomer(username, password, email,
				Converters.convertStringToPaymentMethod(defaultPaymentMethod),sGallery);
		return Converters.convertToDto(customer);
	}

	@PostMapping(value = { "/customer/delete/{username}", "/customer/delete/{username}" })
	public CustomerDTO deleteCustomer(@PathVariable("username") String username) {
		Customer customer = registrationService.deleteCustomer(username);
		return Converters.convertToDto(customer);
	}
	
	@GetMapping(value = { "/customer/name/{username}", "/customer/name/{username}/" })
	public CustomerDTO getCustomerByUsername(@PathVariable("username") String username) {
		Customer customer = registrationService.getCustomer(username);
		return Converters.convertToDto(customer);
	}
	
	@GetMapping(value = { "/customer/email/{email}", "/customer/email/{email}/" })
	public CustomerDTO getCustomerByEmail(@PathVariable("email") String email) {
		Customer customer = registrationService.getCustomerByEmail(email);
		return Converters.convertToDto(customer);
	}


	@GetMapping(value = { "/artist", "/artist/" })
	public List<ArtistDTO> getAllArtists() {
		return registrationService.getAllArtists().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}
	
	@GetMapping(value = { "/Artist/name/{username}", "/Artist/email/{username}/" })
	public ArtistDTO getArtistByUsername(@PathVariable("username") String username) {
		Artist artist = registrationService.getArtist(username);
		return Converters.convertToDto(artist);
	}
	
	@GetMapping(value = { "/Artist/email/{email}", "/Artist/email/{email}/" })
	public ArtistDTO getArtistByEmail(@PathVariable("email") String email) {
		Artist artist = registrationService.getArtistByEmail(email);
		return Converters.convertToDto(artist);
	}

	@PostMapping(value = { "/artist/{username}", "/artist/{username}/" })
	public ArtistDTO createArtist(@PathVariable("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("defaultPaymentMethod") String defaultPaymentMethod,
			@RequestParam("creationDate") Date creationDate) throws IllegalArgumentException {
		SmartGallery sGallery = browsingService.getAllSmartGalleries().get(0);
		Artist artist = registrationService.createArtist(username, password, email,
				Converters.convertStringToPaymentMethod(defaultPaymentMethod),sGallery);
		return Converters.convertToDto(artist);
	}

	@PostMapping(value = { "/artist/delete/{username}", "/artist/delete/{username}" })
	public ArtistDTO deleteArtist(@PathVariable("username") String username) {
		Artist artist = registrationService.deleteArtist(username);
		return Converters.convertToDto(artist);
	}
}
