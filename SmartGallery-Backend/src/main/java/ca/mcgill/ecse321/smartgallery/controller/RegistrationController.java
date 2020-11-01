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

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.dto.*;
import ca.mcgill.ecse321.smartgallery.model.*;
import ca.mcgill.ecse321.smartgallery.service.RegistrationService;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationController {
	
	@Autowired
	private ArtistRepository artistRepository;
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
	private RegistrationService registrationService;
	
	@PostMapping(value = {"/login", "/login/"})
	public boolean login(@RequestParam(name = "username") String username, 
			@RequestParam(name = "password") String password) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if (customer != null) {
			registrationService.login(customer, password);
		} else {
			Artist artist = artistRepository.findArtistByUsername(username);
			if (artist != null) {
				return registrationService.login(artist, password);
			}
		}
		return false;
	}
	
	@PostMapping(value = {"/customer/login", "/customer/login/"})
	public boolean customerLogin(@RequestParam(name = "username") String username, 
			@RequestParam(name = "password") String password) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if (customer != null) {
			return registrationService.login(customer, password);
		}
		return false;
	}
	
	@PostMapping(value = {"/artist/login", "/artist/login/"})
	public boolean artistLogin(@RequestParam(name = "username") String username, 
			@RequestParam(name = "password") String password) {
		Artist artist = artistRepository.findArtistByUsername(username);
		if (artist != null) {
			registrationService.login(artist, password);
		}
		return false;
	}
	
	@PostMapping(value = {"/logout", "/logout/"})
	public boolean logout(@RequestParam(name = "username") String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if (customer != null) {
			registrationService.logout(customer);
			return true;
		} else {
			Artist artist = artistRepository.findArtistByUsername(username);
			if (artist != null) {
				registrationService.logout(artist);
				return true;
			}
		}
		return false;
	}

	
	@PostMapping(value = {"/customer/logout", "/customer/logout/"})
	public boolean customerLogout(@RequestParam(name = "username") String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		if (customer != null) {
			registrationService.logout(customer);
			return true;
		}
		return false;
	}
	
	@PostMapping(value = {"/artist/logout", "/artist/logout/"})
	public boolean artistLogout(@RequestParam(name = "username") String username) {
		Artist artist = artistRepository.findArtistByUsername(username);
		if (artist != null) {
			registrationService.logout(artist);
			return true;
		}
		return false;
	}
	
	@PostMapping(value = {"/email/change","/email/change/"})
	public boolean changeEmail(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, 
			@RequestParam(name = "newEmail") String email) {
		Profile profile = customerRepository.findCustomerByUsername(username);
		if (profile == null) {
			profile = artistRepository.findArtistByUsername(username);
		}
		if (profile == null) {
			return false;
		}
		return registrationService.updateEmail(profile, email, password);
	}
	
	@PostMapping(value = {"/password/change","/password/change/"})
	public boolean changePassword(@RequestParam(name = "username") String username,
			@RequestParam(name = "oldPassword") String oldPassword, 
			@RequestParam(name = "newPassword") String newPassword) {
		Profile profile = customerRepository.findCustomerByUsername(username);
		if (profile == null) {
			profile = artistRepository.findArtistByUsername(username);
		}
		if (profile == null) {
			return false;
		}
		return registrationService.updatePassword(profile, oldPassword, newPassword);
	}

	@GetMapping(value = { "/customer", "/customer/" })
	public List<CustomerDTO> getAllCustomers() {
		return registrationService.getAllCustomers().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@PostMapping(value = { "/customer/{username}/{password}/{email}/{defaultPaymentMethod}" })
	public CustomerDTO createCustomer(@PathVariable("username") String username,
			@PathVariable("password") String password, @PathVariable("email") String email,
			@PathVariable("defaultPaymentMethod") String defaultPaymentMethod) 
			throws IllegalArgumentException {
		Customer customer = registrationService.createCustomer(username, password, email, defaultPaymentMethod);
		return Converters.convertToDto(customer);
	}

	@PostMapping(value = { "/customer/delete/{username}", "/customer/delete/{username}" })
	public CustomerDTO deleteCustomer(@PathVariable("username") String username) {
		Customer customer = registrationService.deleteCustomer(username);
		return Converters.convertToDto(customer);
	}

	@GetMapping(value = { "/artist", "/artist/" })
	public List<ArtistDTO> getAllArtists() {
		return registrationService.getAllArtists().stream().map(p -> Converters.convertToDto(p))
				.collect(Collectors.toList());
	}

	@PostMapping(value = { "/artist/{username}", "/artist/{username}/" })
	public ArtistDTO createArtist(@PathVariable("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("defaultPaymentMethod") String defaultPaymentMethod) 
			throws IllegalArgumentException {
		Artist artist = registrationService.createArtist(username, password, email, defaultPaymentMethod);
		return Converters.convertToDto(artist);
	}

	@PostMapping(value = { "/artist/delete/{username}", "/artist/delete/{username}" })
	public ArtistDTO deleteArtist(@PathVariable("username") String username) {
		Artist artist = registrationService.deleteArtist(username);
		return Converters.convertToDto(artist);
	}
}
