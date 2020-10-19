package ca.mcgill.ecse321.smartgallery.dto;

public class GalleryDTO {
	
	private SmartGalleryDTO smartGallery;
	private String galleryName;
	private double commissionPercentage;
	
	
	public GalleryDTO() {
		
	}
	
	
	public GalleryDTO(SmartGalleryDTO smartGallery,String galleryName, double commissionPercentage) {
		this.smartGallery = smartGallery;
		this.galleryName = galleryName;
		this.commissionPercentage = commissionPercentage;
	}

	//Could add other constructors for 0 associations

	/**
	 * @return the smartGallery
	 */
	public SmartGalleryDTO getSmartGallery() {
		return smartGallery;
	}


	/**
	 * @return the galleryName
	 */
	public String getGalleryName() {
		return galleryName;
	}


	/**
	 * @return the commissionPercentage
	 */
	public double getCommissionPercentage() {
		return commissionPercentage;
	}

	
	
	
}
