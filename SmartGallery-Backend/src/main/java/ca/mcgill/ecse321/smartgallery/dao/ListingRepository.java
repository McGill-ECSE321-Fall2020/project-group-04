package ca.mcgill.ecse321.smartgallery.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.*;

@RepositoryRestResource(collectionResourceRel = "listing_data", path = "listing_data")
public interface ListingRepository extends CrudRepository<Listing, Integer> {

	Listing findListingByListingID(Integer listingID);

}
