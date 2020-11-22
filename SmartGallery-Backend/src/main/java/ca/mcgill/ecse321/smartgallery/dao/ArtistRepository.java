package ca.mcgill.ecse321.smartgallery.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.Artist;

@RepositoryRestResource(collectionResourceRel = "artist_data", path = "artist_data")
public interface ArtistRepository extends CrudRepository<Artist, String> {
	
	Artist findArtistByUsername(String username);
	
	Artist findArtistByEmail(String email);
	
	List<Artist> findArtistByIsVerified(boolean isVerified);
	
	List<Artist> findArtistByUsernameContaining(String usernameFragment);
	
}