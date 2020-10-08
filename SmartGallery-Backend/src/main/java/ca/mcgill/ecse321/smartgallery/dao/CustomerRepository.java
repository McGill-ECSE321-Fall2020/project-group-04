package ca.mcgill.ecse321.smartgallery.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.smartgallery.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {

	Customer findCustomerByUsername(String username);

	Customer findCustomerByEmail(String email);

}