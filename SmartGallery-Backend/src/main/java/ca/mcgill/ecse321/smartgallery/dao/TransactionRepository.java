package ca.mcgill.ecse321.smartgallery.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.*;

@RepositoryRestResource(collectionResourceRel = "transaction_data", path = "transaction_data")
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	Transaction findTransactionByTransactionID(Integer id);
}
