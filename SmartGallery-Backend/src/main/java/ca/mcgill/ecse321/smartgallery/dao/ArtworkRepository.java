package ca.mcgill.ecse321.smartgallery.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.ArtStyle;
import ca.mcgill.ecse321.smartgallery.model.Artwork;

@RepositoryRestResource(collectionResourceRel = "artwork_data", path = "artwork_data")
public interface ArtworkRepository extends CrudRepository<Artwork, Integer> {
	
	Artwork findArtworkByArtworkID(int artworkID);
	
	List<Artwork> findArtworkByName(String name);
	
	List<Artwork> findArtworkByNameIgnoreCase(String name);
	
	List<Artwork> findArtworkByNameContaining(String nameFragment);
	
	List<Artwork> findArtworkByStyle(ArtStyle artStyle);
	
	List<Artwork> findArtworkByWeightBetween(int minWeight, int maxWeight);
	
	List<Artwork> findArtworkByHeightBetween(int minHeight, int maxHeight);
	
	List<Artwork> findArtworkByNameContainingAndPriceBetween(String nameFragment, double minPrice, double maxPrice);
	
	List<Artwork> findArtworkByNameContainingAndStyle(String nameFragment, ArtStyle artStyle);
	
	List<Artwork> findArtworkByIsBeingPromoted(boolean isBeingPromoted);
	
	
}