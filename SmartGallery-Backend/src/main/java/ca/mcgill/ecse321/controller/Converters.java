package ca.mcgill.ecse321.controller;

import java.util.HashSet;
import java.util.Set;

import ca.mcgill.ecse321.smartgallery.dto.ArtistDTO;
import ca.mcgill.ecse321.smartgallery.dto.ArtworkDTO;
import ca.mcgill.ecse321.smartgallery.dto.CustomerDTO;
import ca.mcgill.ecse321.smartgallery.dto.GalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.ListingDTO;
import ca.mcgill.ecse321.smartgallery.dto.ProfileDTO;
import ca.mcgill.ecse321.smartgallery.dto.SmartGalleryDTO;
import ca.mcgill.ecse321.smartgallery.dto.TransactionDTO;
import ca.mcgill.ecse321.smartgallery.model.Artist;
import ca.mcgill.ecse321.smartgallery.model.Artwork;
import ca.mcgill.ecse321.smartgallery.model.Customer;
import ca.mcgill.ecse321.smartgallery.model.Gallery;
import ca.mcgill.ecse321.smartgallery.model.Listing;
import ca.mcgill.ecse321.smartgallery.model.Profile;
import ca.mcgill.ecse321.smartgallery.model.SmartGallery;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

public class Converters {

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
				a.getEmail(), a.getDefaultPaymentMethod(), a.getCreationDate(), a.isIsVerified());
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
				c.getEmail(), c.getDefaultPaymentMethod(), c.getCreationDate());
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
				a.getArtworkID());
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

	
	/**
	 * Converts a set of profiles to its DTO equivalent
	 * 
	 * @param profile The set of profiles to convert
	 * @return	Converted ProfileDTO set
	 */
	public static Set<ProfileDTO> convertToDto(Set<Profile> profile) {
		
		HashSet<ProfileDTO> profiles = new HashSet<ProfileDTO>();
		
		for(Profile p: profile) {
			if(p instanceof Artist) {
				profiles.add(convertToDto((Artist)p));
			}else if (p instanceof Customer) {
				profiles.add(convertToDto((Customer)p));
			}
		}
		
		return profiles;
	}

	/**
	 * Converts transaction object to its DTO equivalent
	 * 
	 * @param transaction The transaction to convert
	 * @return	The TransactionDTO
	 */
	public static TransactionDTO convertToDto(Transaction transaction) {
		if (transaction == null) {
			throw new IllegalArgumentException("There is no such transaction.");
		}

		return new TransactionDTO(convertToDto(transaction.getSmartGallery()), convertToDto(transaction.getListing()),
				convertToDto(transaction.getProfile()), transaction.getTransactionID(), transaction.getPaymentMethod(),
				transaction.getDeliveryMethod(), transaction.getPaymentDate());
	}
}
