package ca.mcgill.ecse321.smartgallery.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.Artist;

public interface ArtistRepository extends CrudRepository<Artist, String> {
	
	Artist findArtistByUsername(String username);
	
	Artist findArtistByEmail(String email);
	
}