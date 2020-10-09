package ca.mcgill.ecse321.smartgallery.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, String> {
	
	Artwork findArtworkByArtworkID(int artworkID);
	
	List<Artwork> findArtworkByName(String name);
	
	List<Artwork> findArtworkByNameIgnoreCase(String name);
	
}