package ca.mcgill.ecse321.smartgallery.dao;


import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.Profile;
import ca.mcgill.ecse321.smartgallery.model.Transaction;

@RepositoryRestResource(collectionResourceRel = "transaction_data", path = "transaction_data")
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	Transaction findTransactionByTransactionID(Integer id);
	List<Transaction> findTransactionByPaymentDate(Date date);
	List<Transaction> findTransactionByProfile(Profile profile);
}
