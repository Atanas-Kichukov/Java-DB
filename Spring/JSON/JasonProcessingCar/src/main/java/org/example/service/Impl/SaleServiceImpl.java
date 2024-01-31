package org.example.service.Impl;

import com.google.gson.Gson;
import org.example.model.dto.CarSeedDto;
import org.example.model.dto.SaleSeedDto;
import org.example.model.entity.Sale;
import org.example.repository.SaleRepository;
import org.example.service.CarService;
import org.example.service.CustomerService;
import org.example.service.SaleService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {
        private static final double[] DISCOUNT = new double[]{0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5};
        private final Gson gson;
        private final CarService carService;
        private final CustomerService customerService;
        private final SaleRepository saleRepository;
        private final ValidationUtil validationUtil;
        private final ModelMapper modelMapper;

    public SaleServiceImpl(Gson gson, CarService carService, CustomerService customerService, SaleRepository saleRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.gson = gson;
        this.carService = carService;
        this.customerService = customerService;
        this.saleRepository = saleRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSale() {
        if(saleRepository.count()>0) {
            return;
        }
        for (int i = 0; i < 19; i++) {
            SaleSeedDto saleSeedDto = new SaleSeedDto();
            saleSeedDto.setCar(carService.getRandomCar());
            saleSeedDto.setCustomer(customerService.getRandomCustomer());
            saleSeedDto.setDiscountPercentage(getRandomDiscount());
            Sale sale
                    = modelMapper.map(saleSeedDto, Sale.class);
            saleRepository.save(sale);
        }
    }

    private Double getRandomDiscount() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0,DISCOUNT.length);
        return DISCOUNT[randomNumber];
    }
}
