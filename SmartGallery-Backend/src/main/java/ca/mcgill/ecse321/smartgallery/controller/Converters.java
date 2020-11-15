package ca.mcgill.ecse321.smartgallery.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.smartgallery.dao.ArtistRepository;
import ca.mcgill.ecse321.smartgallery.dao.ArtworkRepository;
import ca.mcgill.ecse321.smartgallery.dao.CustomerRepository;
import ca.mcgill.ecse321.smartgallery.dao.GalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.ListingRepository;
import ca.mcgill.ecse321.smartgallery.dao.SmartGalleryRepository;
import ca.mcgill.ecse321.smartgallery.dao.TransactionRepository;
import ca.mcgill.ecse321.smartgallery.dto.ArtistDTO;
import ca.mcgill.ecse321.smartgallery.dto.ArtworkDTO;
import ca.mcgill.ecse321.smartgallery.dto.CustomerDTO;
import ca.mcgill.ecse321.smartgallery.dto.GalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.ListingDTO;
import ca.mcgill.ecse321.smartgallery.dto.ProfileDTO;
import ca.mcgill.ecse321.smartgallery.dto.SmartGalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.TransactionDTO;
import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.DeliveryMethod;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.PaymentMethod;
import ca.mcgill.ecse321.smartgallery.model.Profile;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

public class Converters {

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

	/*
	 * ========================== DTO CONVERTERS ==========================
	 */

	/**
	 * Converts smart gallery to its DTO equivalent
	 * 
	 * @param s The smart gallery to convert
	 * @return SmartGalleryDTO
	 */
	public static SmartGalleryDTO convertToDto(SmartGallery s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such SmartGallery.");
		}
		SmartGalleryDTO smartGalleryDTO = new SmartGalleryDTO(s.getSmartGalleryID());
		return smartGalleryDTO;
	}

	/**
	 * Converts gallery to its DTO equivalent
	 * 
	 * @param g The gallery object to convert
	 * @return The GalleryDTO
	 */
	public static GalleryDTO convertToDto(Gallery g) {
		if (g == null) {
			throw new IllegalArgumentException("There is no such SmartGallery.");
		}
		GalleryDTO galleryDTO = new GalleryDTO(convertToDto(g.getSmartGallery()), g.getGalleryName(),
				g.getComissionPercentage());
		return galleryDTO;
	}

	/**
	 * Converts artist to its DTO equivalent
	 * 
	 * @param a The artist object to convert
	 * @return The ArtistDTO
	 */
	public static ArtistDTO convertToDto(Artist a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Artist.");
		}
		ArtistDTO artistDTO = new ArtistDTO(convertToDto(a.getSmartGallery()), a.getUsername(), a.getPassword(),
				a.getEmail(), a.getDefaultPaymentMethod(), a.getCreationDate(), a.isLoggedIn(), a.isIsVerified());
		if (a.getArtworks() != null) {
			for (Artwork art : a.getArtworks()) {
				artistDTO.addArtworks(withoutArtist(art));
			}
		}

		Set<TransactionDTO> tSet = new HashSet<>();
		if (a.getTransaction() != null) {
			for (Transaction t : a.getTransaction()) {
				tSet.add(convertWithoutProfile(t));
			}
		}
		artistDTO.setTransaction(tSet);

		Set<ArtworkDTO> artSet = new HashSet<>();
		if (a.getArtworksViewed() != null) {
			for (Artwork art : a.getArtworksViewed()) {
				if (!art.getArtists().contains(a)) {
					artSet.add(convertToDto(art));
				}
			}
		}
		artistDTO.setArtworksViewed(artSet);
		return artistDTO;
	}

	/**
	 * Converts customer to its DTO equivalent
	 * 
	 * @param c The customer object to convert
	 * @return The CustomerDTO
	 */
	public static CustomerDTO convertToDto(Customer c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Customer.");
		}
		CustomerDTO customerDto = new CustomerDTO(convertToDto(c.getSmartGallery()), c.getUsername(), c.getPassword(),
				c.getEmail(), c.getDefaultPaymentMethod(), c.getCreationDate(), c.isLoggedIn());
		Set<ArtworkDTO> artSet = new HashSet<>();
		if (c.getArtworksViewed() != null) {
			for (Artwork art : c.getArtworksViewed()) {
				artSet.add(convertToDto(art));
			}
			customerDto.setArtworksViewed(artSet);
		}

		Set<TransactionDTO> tSet = new HashSet<>();
		if (c.getTransaction() != null) {
			for (Transaction t : c.getTransaction()) {
				tSet.add(convertWithoutProfile(t));
			}
		}

		customerDto.setTransaction(tSet);
		return customerDto;
	}

	/**
	 * Converts artwork to its DTO equivalent
	 * 
	 * @param a The artwork object to convert
	 * @return The ArtworkDTO
	 */
	public static ArtworkDTO convertToDto(Artwork a) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Artwork.");
		}
		Set<Artist> artists = a.getArtists();
		Set<ArtistDTO> artistsDTO = new HashSet<>();
		for (Artist ar : artists) {
			artistsDTO.add(convertToDto(ar));
		}
		ArtworkDTO artworkDTO = new ArtworkDTO(artistsDTO, convertToDto(a.getGallery()), a.getName(), a.getYear(),
				a.getPrice(), a.isIsBeingPromoted(), a.getStyle(), a.getHeight(), a.getWeight(), a.getWidth(),
				a.getImageUrl(), a.getArtworkID());

		if (a.getListing() != null) {
			ListingDTO l = listingNoArtwork(a.getListing());
			artworkDTO.setListing(l);
		}

		return artworkDTO;
	}

	public static ArtworkDTO withoutArtist(Artwork a) {
		ArtworkDTO artworkDTO = new ArtworkDTO(convertToDto(a.getGallery()), a.getName(), a.getYear(), a.getPrice(),
				a.isIsBeingPromoted(), a.getStyle(), a.getHeight(), a.getWeight(), a.getWidth(), a.getImageUrl(),
				a.getArtworkID());
		if (a.getListing() != null) {
			ListingDTO l = listingNoArtwork(a.getListing());
			artworkDTO.setListing(l);
		}
		return artworkDTO;
	}

	/**
	 * Converts listing to its DTO equivalent
	 * 
	 * @param l The listing object to convert
	 * @return
	 */
	public static ListingDTO convertToDto(Listing l) {
		if (l == null) {
			throw new IllegalArgumentException("There is no such Listing.");
		}
		ListingDTO listingDto = new ListingDTO(convertToDto(l.getGallery()), convertToDto(l.getArtwork()),
				l.getListedDate(), l.isIsSold(), l.getListingID());
		return listingDto;
	}

	public static ListingDTO listingNoArtwork(Listing l) {
		if (l == null) {
			throw new IllegalArgumentException("There is no such Listing.");
		}
		ListingDTO listingDto = new ListingDTO(convertToDto(l.getGallery()), l.getListedDate(), l.isIsSold(),
				l.getListingID());
		return listingDto;
	}

	/**
	 * Converts a set of profiles to its DTO equivalent
	 * 
	 * @param profile The set of profiles to convert
	 * @return Converted ProfileDTO set
	 */
	public static Set<ProfileDTO> convertToDto(Set<Profile> profile) {

		HashSet<ProfileDTO> profiles = new HashSet<ProfileDTO>();

		for (Profile p : profile) {
			if (p instanceof Artist) {
				profiles.add(convertToDto((Artist) p));
			} else if (p instanceof Customer) {
				profiles.add(convertToDto((Customer) p));
			}
		}

		return profiles;
	}

	/**
	 * Converts transaction object to its DTO equivalent
	 * 
	 * @param transaction The transaction to convert
	 * @return The TransactionDTO
	 */
	public static TransactionDTO convertToDto(Transaction transaction) {
		if (transaction == null) {
			throw new IllegalArgumentException("There is no such transaction.");
		}

		Profile p = transaction.getProfile();
		TransactionDTO t;
		if (p instanceof Customer) {
			Customer c = (Customer) p;
			t = new TransactionDTO(convertToDto(transaction.getSmartGallery()), convertToDto(transaction.getListing()),
					convertToDto(c), transaction.getTransactionID(), transaction.getPaymentMethod(),
					transaction.getDeliveryMethod(), transaction.getPaymentDate());
		} else {
			Artist a = (Artist) p;
			t = new TransactionDTO(convertToDto(transaction.getSmartGallery()), convertToDto(transaction.getListing()),
					convertToDto(a), transaction.getTransactionID(), transaction.getPaymentMethod(),
					transaction.getDeliveryMethod(), transaction.getPaymentDate());
		}

		return t;
	}

	/**
	 * Method adds a link from the profile to the transaction
	 * 
	 * @param transaction
	 * @return
	 */
	public static TransactionDTO convertWithoutProfile(Transaction transaction) {
		if (transaction == null) {
			throw new IllegalArgumentException("There is no such transaction.");
		}

		return new TransactionDTO(convertToDto(transaction.getSmartGallery()), convertToDto(transaction.getListing()),
				transaction.getTransactionID(), transaction.getPaymentMethod(), transaction.getDeliveryMethod(),
				transaction.getPaymentDate());
	}

	/**
	 * Converts SmartGalleryDTO to its Object equivalent
	 * 
	 * @param smartGalleryDTO The SmartGalleryDTO to convert
	 * @return smartGallery
	 */
	public static SmartGallery convertToObject(SmartGalleryDTO smartGalleryDTO) {
		if (smartGalleryDTO == null) {
			throw new IllegalArgumentException("There is no such SmartGalleryDTO.");
		}

		SmartGallery smartGallery = smartGalleryRepository
				.findSmartGalleryBySmartGalleryID(smartGalleryDTO.getSmartGalleryID());
		return smartGallery;
	}

	/**
	 * Converts GalleryDTO to its Object equivalent
	 * 
	 * @param galleryDTO The GalleryDTO to convert
	 * @return gallery
	 */
	public static Gallery convertToObject(GalleryDTO galleryDTO) {
		if (galleryDTO == null) {
			throw new IllegalArgumentException("There is no such GalleryDTO.");
		}

		Gallery gallery = galleryRepository.findGalleryByGalleryName(galleryDTO.getGalleryName());
		return gallery;
	}

	/**
	 * Converts ArtistDTO to its Object equivalent
	 * 
	 * @param artistDTO The ArtistDTO to convert
	 * @return artist
	 */
	public static Artist convertToObject(ArtistDTO artistDTO) {
		if (artistDTO == null) {
			throw new IllegalArgumentException("There is no such GalleryDTO.");
		}

		Artist artist = artistRepository.findArtistByUsername(artistDTO.getUsername());
		return artist;
	}

	/**
	 * Converts ArtworkDTO to its Object equivalent
	 * 
	 * @param artworkDTO The ArtworkDTO to convert
	 * @return artwork
	 */
	public static Artwork convertToObject(ArtworkDTO artworkDTO) {
		if (artworkDTO == null) {
			throw new IllegalArgumentException("There is no such GalleryDTO.");
		}

		Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkDTO.getArtworkID());
		return artwork;
	}

	/**
	 * Converts CustomerDTO to its Object equivalent
	 * 
	 * @param customerDTO The CustomerDTO to convert
	 * @return customer
	 */
	public static Customer convertToObject(CustomerDTO customerDTO) {
		if (customerDTO == null) {
			throw new IllegalArgumentException("There is no such GalleryDTO.");
		}

		Customer customer = customerRepository.findCustomerByUsername(customerDTO.getUsername());
		return customer;
	}

	/**
	 * Converts ListingDTO to its Object equivalent
	 * 
	 * @param listingDTO The ListingDTO to convert
	 * @return listing
	 */
	public static Listing convertToObject(ListingDTO listingDTO) {
		if (listingDTO == null) {
			throw new IllegalArgumentException("There is no such GalleryDTO.");
		}

		Listing listing = listingRepository.findListingByListingID(listingDTO.getListingID());
		return listing;
	}

	/**
	 * Converts TransactionDTO to its Object equivalent
	 * 
	 * @param transactionDTO The ListingDTO to convert
	 * @return transaction
	 */
	public static Transaction convertToObject(TransactionDTO transactionDTO) {
		if (transactionDTO == null) {
			throw new IllegalArgumentException("There is no such GalleryDTO.");
		}

		Transaction transaction = transactionRepository
				.findTransactionByTransactionID(transactionDTO.getTransactionID());
		return transaction;
	}

	/**
	 * Converts a string to its ArtStyle enum equivalent
	 * 
	 * @author OliverStappas
	 * 
	 * @param artStyle The ArtStyle string to convert
	 * @return ArtStyle
	 */
	public static ArtStyle convertStringToArtStyle(String artStyle) {
		switch (artStyle.toLowerCase()) {
		case "realist":
			return ArtStyle.REALIST;
		case "renaissance":
			return ArtStyle.RENAISSANCE;
		case "surrealist":
			return ArtStyle.SURREALIST;
		case "impressionist":
			return ArtStyle.IMPRESSIONIST;
		default:
			throw new IllegalArgumentException("No corresponding art style to input");
		}
	}

	/**
	 * Converts a string to its DeliveryMethod enum equivalent
	 * 
	 * @author OliverStappas
	 * 
	 * @param deliveryMethod The DeliveryMethod string to convert
	 * @return DeliveryMethod
	 */
	public static DeliveryMethod convertStringToDeliveryMethod(String deliveryMethod) {
		switch (deliveryMethod.toLowerCase()) {
		case "pickup":
			return DeliveryMethod.PICKUP;
		case "shipping":
			return DeliveryMethod.SHIPPING;
		default:
			throw new IllegalArgumentException("No corresponding delivery method to input");
		}
	}

	/**
	 * Converts a string to its PaymentMethod enum equivalent
	 * 
	 * @author OliverStappas
	 * 
	 * @param paymentMethod The PaymentMethod string to convert
	 * @return PaymentMethod
	 */
	public static PaymentMethod convertStringToPaymentMethod(String paymentMethod) {
		switch (paymentMethod.toLowerCase()) {
		case "credit":
			return PaymentMethod.CREDIT;
		case "paypal":
			return PaymentMethod.PAYPAL;
		default:
			throw new IllegalArgumentException("No corresponding payment method to input");
		}
	}

}
