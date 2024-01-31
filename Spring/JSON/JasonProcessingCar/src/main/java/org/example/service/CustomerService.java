package org.example.service;

import org.example.model.dto.CustomerWithAtLeastOneCarDto;
import org.example.model.dto.OrderCustomerInfoDto;
import org.example.model.entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    void seedCustomer() throws IOException;
    Customer getRandomCustomer();
    List<OrderCustomerInfoDto> findAllCustomerAndOrderByBirthDate();
    List<CustomerWithAtLeastOneCarDto> findAllWithAtLeastOneSale();
}
