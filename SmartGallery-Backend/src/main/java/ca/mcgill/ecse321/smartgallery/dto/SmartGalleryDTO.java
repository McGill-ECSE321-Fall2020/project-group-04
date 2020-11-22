package ca.mcgill.ecse321.smartgallery.dto;

public class SmartGalleryDTO {
	private int smartGalleryID;
	
	public SmartGalleryDTO() {
	}
	
	public SmartGalleryDTO(int id) {
		this.smartGalleryID = id;
	}

	/**
	 * @return the smartGalleryID
	 */
	public int getSmartGalleryID() {
		return smartGalleryID;
	}
}
