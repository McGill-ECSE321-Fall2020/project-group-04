package ca.mcgill.ecse321.smartgallery.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.*;
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

	Transaction findTransactionByTransactionID(Integer id);
}
