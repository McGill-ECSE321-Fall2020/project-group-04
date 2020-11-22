package ca.mcgill.ecse321.smartgallery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.*;

@RepositoryRestResource(collectionResourceRel = "gallery_data", path = "gallery_data")
public interface GalleryRepository extends CrudRepository<Gallery, String> {

	Gallery findGalleryByGalleryName(String name);
}
