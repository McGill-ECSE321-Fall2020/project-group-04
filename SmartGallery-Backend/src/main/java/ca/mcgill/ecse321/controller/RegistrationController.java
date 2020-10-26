package ca.mcgill.ecse321.controller;


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

import ca.mcgill.ecse321.service.RegistrationService;
import ca.mcgill.ecse321.smartgallery.dto.*;
import ca.mcgill.ecse321.smartgallery.model.*;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@GetMapping(value = { "/customer", "/customer/" })
	public List<CustomerDTO> getAllCustomers() {
		return registrationService.getAllCustomers().stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}
	
	@PostMapping(value = { "/customer/{customerID}", "/customer/{customerID}/" })
	public CustomerDTO createCustomer(@PathVariable("username") String username, @PathVariable("password") String password, 
		@PathVariable("email") String email, @RequestParam PaymentMethod defaultPaymentMethod, @RequestParam Date creationDate,
		@RequestParam(name = "smartGallery") SmartGallery smartGallery) throws IllegalArgumentException {
		Customer customer = registrationService.createCustomer(username, password, email, defaultPaymentMethod, 
		creationDate, smartGallery);
		return convertToDto(customer);
	}
	
	private SmartGalleryDTO convertToDto(SmartGallery s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such SmartGallery.");
		}
		SmartGalleryDTO smartGalleryDTO = new SmartGalleryDTO(s.getSmartGalleryID());
		return smartGalleryDTO;
	}
	
	private CustomerDTO convertToDto(Customer c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Customer!");
		}
		CustomerDTO customerDto = new CustomerDTO(convertToDto(c.getSmartGallery()), c.getUsername(), c.getPassword(),
				c.getEmail(), c.getDefaultPaymentMethod(), c.getCreationDate());
		return customerDto;
	}
	
}
