package org.example;


import com.google.gson.Gson;
import org.example.Constants.GlobalPath;
import org.example.model.dto.*;
import org.example.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.example.Constants.GlobalPath.PATH_TO_FILES;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final BufferedReader bufferedReader;
    private final Gson gson;

    private static final String PATH_TO_OUTPUT = "src/main/resources/output/";
    private static final String ORDERED_CUSTOMERS = "ordered-customers.json";
    private static final String CARS_FROM_MAKE_TOYOTA ="cars-toyota.json";
    private static final String LOCAL_SUPPLIERS = "local-suppliers.json";
    private static final String CARS_WITH_THEIR_PARTS ="cats-with-parts.json";
    private static final String TOTAL_SALES_BY_CUSTOMERS = "total-sales.json";

    public CommandLineRunnerImpl(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, Gson gson) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.gson = gson;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        System.out.println("Please enter exercise number (From 1 -6)");
        int exerciseNumber = Integer.parseInt(bufferedReader.readLine());
        if(exerciseNumber<1 || exerciseNumber>6){
            System.out.println("Invalid exercise number,enter from 1-5.");
        }
        while(true) {
            switch (exerciseNumber) {
                case 1 -> orderedCustomers();
                case 2 -> carsFromMakeToyota();
                case 3 -> localSuppliers();
                case 4 -> carsWithTheirParts();
                case 5 -> totalSaleByCustomer();
            }
        }

    }

    private void totalSaleByCustomer() throws IOException {
        List<CustomerWithAtLeastOneCarDto> allWithAtLeastOneSale = customerService.findAllWithAtLeastOneSale();
        String content = gson.toJson(allWithAtLeastOneSale);
        writeToFile(TOTAL_SALES_BY_CUSTOMERS+ CARS_WITH_THEIR_PARTS,content);
    }

    private void carsWithTheirParts() throws IOException {
        List<CarWithPartsDto> allCarsWithTheirParts = carService.findAllCarsWithTheirParts();
        String content = gson.toJson(allCarsWithTheirParts);
        writeToFile(PATH_TO_OUTPUT+ CARS_WITH_THEIR_PARTS,content);
    }

    private void localSuppliers() throws IOException {
        List<LocalSupplierDto>localSupplierDtos =supplierService.getAllSuppliersWhichDontImport();
        String content = gson.toJson(localSupplierDtos);
        writeToFile(PATH_TO_OUTPUT + LOCAL_SUPPLIERS,content);
    }

    private void carsFromMakeToyota() throws IOException {
        List<CarMakeDto> toyota = carService.findAllCarsByMake("Toyota");
        String content = gson.toJson(toyota);
        writeToFile(PATH_TO_OUTPUT + CARS_FROM_MAKE_TOYOTA,content);
    }

    private void orderedCustomers() throws IOException {
        List<OrderCustomerInfoDto> orderCustomerInfoDtos = customerService
                .findAllCustomerAndOrderByBirthDate();
        String content = gson.toJson(orderCustomerInfoDtos);
        writeToFile( PATH_TO_OUTPUT + ORDERED_CUSTOMERS, content);
    }

    private void writeToFile(String path, String content) throws IOException {
        Files.write(Path.of(path), Collections.singleton(content));
    }

    private void seedData() throws IOException {
        supplierService.seedSupplier();
        partService.seedPart();
        carService.seedCar();
        customerService.seedCustomer();
        saleService.seedSale();
    }
}
