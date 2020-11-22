package ca.mcgill.ecse321.smartgallery.dto;

import java.sql.Date;

public class ListingDTO {
	
	private GalleryDTO gallery;
	private ArtworkDTO artwork;
	private Date listedDate;
	private boolean isSold;
	private int listingID;
	
	
	
	public ListingDTO() {
		
	}


	public ListingDTO(GalleryDTO gallery, ArtworkDTO artwork, Date listedDate, boolean isSold, int listingID) {
		this.gallery = gallery;
		this.artwork = artwork;
		this.listedDate = listedDate;
		this.isSold = isSold;
		this.listingID = listingID;
	}
	
	public ListingDTO(GalleryDTO gallery, Date listedDate, boolean isSold, int listingID) {
		this.gallery = gallery;
		this.listedDate = listedDate;
		this.isSold = isSold;
		this.listingID = listingID;
	}



	/**
	 * @return the gallery
	 */
	public GalleryDTO getGallery() {
		return gallery;
	}


	/**
	 * @return the artwork
	 */
	public ArtworkDTO getArtwork() {
		return artwork;
	}


	/**
	 * @return the listedDate
	 */
	public Date getListedDate() {
		return listedDate;
	}


	/**
	 * @return the if the listing has sold
	 */
	public boolean isSold() {
		return isSold;
	}


	/**
	 * @return the listingID
	 */
	public int getListingID() {
		return listingID;
	}


	
	
}
