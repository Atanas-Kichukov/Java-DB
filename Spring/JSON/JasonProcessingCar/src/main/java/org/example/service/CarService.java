package org.example.service;

import org.example.model.dto.CarMakeDto;
import org.example.model.dto.CarWithPartsDto;
import org.example.model.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {

    void seedCar() throws IOException;

    Car getRandomCar();
    List<CarMakeDto> findAllCarsByMake(String make);
    List<CarWithPartsDto> findAllCarsWithTheirParts();
}
