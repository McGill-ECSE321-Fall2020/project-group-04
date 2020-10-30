package ca.mcgill.ecse321.smartgallery.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.SmartGallery;

@RepositoryRestResource(collectionResourceRel = "smartGallery_data", path = "smartGallery_data")
public interface SmartGalleryRepository extends CrudRepository<SmartGallery, Integer> {
	
	SmartGallery findSmartGalleryBySmartGalleryID(int smartGalleryID);

}
