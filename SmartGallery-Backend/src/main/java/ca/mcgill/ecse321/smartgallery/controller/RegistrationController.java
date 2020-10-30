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

import ca.mcgill.ecse321.smartgallery.dto.*;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.smartgallery.service.RegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;

	@GetMapping(value = { "/customer", "/customer/" })
	public List<CustomerDTO> getAllCustomers() {
		return registrationService.getAllCustomers().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@PostMapping(value = { "/customer/{username}", "/customer/{username}/" })
	public CustomerDTO createCustomer(@PathVariable("username") String username,
			@PathVariable("password") String password, @PathVariable("email") String email,
			@RequestParam PaymentMethod defaultPaymentMethod, @RequestParam Date creationDate,
			@RequestParam(name = "smartGallery") SmartGallery smartGallery) throws IllegalArgumentException {
		Customer customer = registrationService.createCustomer(username, password, email, defaultPaymentMethod);
		return Converters.convertToDto(customer);
	}
	
	
	@PostMapping(value = { "/customer/delete/{username}","/customer/delete/{username}"})
	public CustomerDTO deleteCustomer(@PathVariable("username") String username) {
		Customer customer = registrationService.deleteCustomer(username);
		return Converters.convertToDto(customer);
	}
	
	@PostMapping(value = { "/artist/delete/{username}","/artist/delete/{username}"})
	public ArtistDTO deleteArtist(@PathVariable("username") String username) {
		Artist artist = registrationService.deleteArtist(username);
		return Converters.convertToDto(artist);
	}
}