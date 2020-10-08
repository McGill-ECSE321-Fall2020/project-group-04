package ca.mcgill.ecse321.smartgallery.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
	
	Customer findCustomerByUserame(String username);
	
	Customer findCustomerByEmail(String email);

}
