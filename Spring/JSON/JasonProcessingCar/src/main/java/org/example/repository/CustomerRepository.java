package org.example.repository;

import org.example.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
@Query("Select c FROM Customer c Order By c.dateOfBirth,c.youngDriver")
List<Customer> findAllOrderByDateOfBirth();
@Query("Select c from Customer c " +
        "Where (Select COUNT(s) FROM Sale s Where s.customer.id = c.id)> 0 ")
List<Customer> findAllWithAtLeastOneSale();



}
