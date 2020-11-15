package ca.mcgill.ecse321.smartgallery.dto;

import java.util.Set;

import ca.mcgill.ecse321.smartgallery.model.ArtStyle;

public class ArtworkDTO {
	private Set<ArtistDTO> artists;
	private GalleryDTO gallery;
	private String name;
	private int year;
	private double price;
	private boolean isBeingPromoted;
	private ArtStyle artStyle;
	private int height;
	private int weight;
	private int width;
	private int artworkID;
	private String imageUrl;
	private ListingDTO listing;

	public ArtworkDTO(){
		
	}
	
	
	public ArtworkDTO(Set<ArtistDTO> artists, GalleryDTO gallery, String name, int year, double price, boolean isBeingPromoted, ArtStyle style, int height,
			int weight, int width, String imageUrl, int artworkID) {
		this.artists = artists;
		this.gallery = gallery;
		this.name = name;
		this.year = year;
		this.price = price;
		this.isBeingPromoted = isBeingPromoted;
		this.artStyle = style;
		this.height = height;
		this.weight = weight;
		this.width = width;
		this.artworkID = artworkID;
		this.imageUrl = imageUrl;
	}

	public ArtworkDTO(GalleryDTO gallery, String name, int year, double price, boolean isBeingPromoted, ArtStyle style, int height,
			int weight, int width, String imageUrl, int artworkID) {
		this.gallery = gallery;
		this.name = name;
		this.year = year;
		this.price = price;
		this.isBeingPromoted = isBeingPromoted;
		this.artStyle = style;
		this.height = height;
		this.weight = weight;
		this.width = width;
		this.artworkID = artworkID;
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the artists
	 */
	public Set<ArtistDTO> getArtists() {
		return artists;
	}


	/**
	 * @return the gallery
	 */
	public GalleryDTO getGallery() {
		return gallery;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}


	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * @return the isBeingPromoted
	 */
	public boolean isBeingPromoted() {
		return isBeingPromoted;
	}


	/**
	 * @return the artStyle
	 */
	public ArtStyle getArtStyle() {
		return artStyle;
	}


	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}


	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}


	/**
	 * @return the artworkID
	 */
	public int getArtworkID() {
		return artworkID;
	}


	/**
	 * @return the listing
	 */
	public ListingDTO getListing() {
		return listing;
	}


	/**
	 * @param listing the listing to set
	 */
	public void setListing(ListingDTO listing) {
		this.listing = listing;
	}
	
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
	  return imageUrl;
	}
	
	/**
	 * @param imageUrl
	 */
	public void setImageUrl(String imageUrl) {
	  this.imageUrl = imageUrl;
	}
	
	
	
}
