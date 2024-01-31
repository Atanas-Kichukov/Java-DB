package org.example.service.Impl;

import com.google.gson.Gson;
import org.example.Constants.GlobalPath;
import org.example.model.dto.CustomerSeedDto;
import org.example.model.dto.CustomerWithAtLeastOneCarDto;
import org.example.model.dto.OrderCustomerInfoDto;
import org.example.model.entity.Car;
import org.example.model.entity.Customer;
import org.example.model.entity.Part;
import org.example.model.entity.Sale;
import org.example.repository.CustomerRepository;
import org.example.service.CustomerService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.example.Constants.GlobalPath.PATH_TO_FILES;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String PATH_TO_CUSTOMER = "customers.json";
    private final Gson gson;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CustomerServiceImpl(Gson gson, CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.gson = gson;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCustomer() throws IOException {
        if (customerRepository.count() > 0) {
            return;
        }
        String content = Files.readString(Path.of(PATH_TO_FILES + PATH_TO_CUSTOMER));
        CustomerSeedDto[] customerSeedDtos = gson.fromJson(content, CustomerSeedDto[].class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        Arrays.stream(customerSeedDtos).filter(validationUtil::isValid)
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    customer.setDateOfBirth(LocalDate.parse(customerSeedDto.getDateOfBirth(), formatter));
                    return customer;
                }).forEach(customerRepository::save);
    }

    @Override
    public Customer getRandomCustomer() {
        long randomId = ThreadLocalRandom.current().nextLong(1, customerRepository.count() + 1);
        return customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<OrderCustomerInfoDto> findAllCustomerAndOrderByBirthDate() {
        return customerRepository.findAllOrderByDateOfBirth().stream()
                .map(customer -> modelMapper.map(customer, OrderCustomerInfoDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<CustomerWithAtLeastOneCarDto> findAllWithAtLeastOneSale() {
       return customerRepository.findAllWithAtLeastOneSale()
                .stream()
                .map(customer -> {
                    CustomerWithAtLeastOneCarDto customerWithAtLeastOneCarDto = modelMapper.map(customer, CustomerWithAtLeastOneCarDto.class);
                    customerWithAtLeastOneCarDto.setFullName(customer.getName());
                    customerWithAtLeastOneCarDto.setBoughtCars(customer.getSales().size());
                    Set<Sale> sales = customer.getSales();
                    List<Car> cars = sales.stream().map(Sale::getCar).collect(Collectors.toList());
                    double totalSum = cars.stream().mapToDouble(car -> car.getParts().stream().mapToDouble(part -> Double.parseDouble(String.valueOf(part.getPrice()))).sum()).sum();
                    customerWithAtLeastOneCarDto.setSpentMoney(BigDecimal.valueOf(totalSum));
                    return customerWithAtLeastOneCarDto;
                }).collect(Collectors.toList());

    }
}