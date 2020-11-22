package ca.mcgill.ecse321.smartgallery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ca.mcgill.ecse321.smartgallery.model.Customer;

@RepositoryRestResource(collectionResourceRel = "customer_data", path = "customer_data")
public interface CustomerRepository extends CrudRepository<Customer, String> {

	Customer findCustomerByUsername(String username);

	Customer findCustomerByEmail(String email);

}