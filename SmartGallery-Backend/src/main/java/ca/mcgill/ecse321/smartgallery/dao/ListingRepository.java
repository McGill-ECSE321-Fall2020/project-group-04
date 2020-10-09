package ca.mcgill.ecse321.smartgallery.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.*;

public interface ListingRepository extends CrudRepository<Listing, Integer> {

	Listing findListingByID(Integer id);

}
