package ca.mcgill.ecse321.smartgallery.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, String> {
	
	Artwork findArtworkByArtworkID(int artworkID);
	
	List<Artwork> findArtworkByName(String name);
	
	List<Artwork> findArtworkByNameIgnoreCase(String name);
	
	List<Artwork> findArtworkByNameContaining(String nameFragment);
	
	List<Artwork> findArtworkByStyle(ArtStyle artStyle);
	
	List<Artwork> findArtworkByWeightBetween(int minWeight, int maxWeight);
	
	List<Artwork> findArtworkByHeightBetween(int minHeight, int maxHeight);
	
	List<Artwork> findArtworkByNameContainingAndStyle(String nameFragment, ArtStyle artStyle);
	
}