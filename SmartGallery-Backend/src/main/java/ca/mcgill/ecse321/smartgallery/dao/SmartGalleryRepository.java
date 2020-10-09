package ca.mcgill.ecse321.smartgallery.dao;


import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.SmartGallery;

public interface SmartGalleryRepository extends CrudRepository<SmartGallery, String> {
	
	SmartGallery findSmartGalleryBySmartGalleryID(int smartGalleryID);


}
