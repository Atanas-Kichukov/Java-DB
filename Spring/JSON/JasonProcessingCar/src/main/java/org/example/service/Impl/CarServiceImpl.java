package org.example.service.Impl;

import com.google.gson.Gson;
import org.example.model.dto.CarMakeDto;
import org.example.model.dto.CarSeedDto;
import org.example.model.dto.CarWithPartsDto;
import org.example.model.entity.Car;
import org.example.repository.CarRepository;
import org.example.service.CarService;
import org.example.service.PartService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.example.Constants.GlobalPath.PATH_TO_FILES;

@Service
public class CarServiceImpl implements CarService {
    private static final String PATH_TO_CAR = "cars.json";
    private final Gson gson;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final PartService partService;


    public CarServiceImpl(Gson gson, CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, PartService partService) {
        this.gson = gson;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.partService = partService;
    }

    @Override
    public void seedCar() throws IOException {
        if(carRepository.count()>0){
            return;
        }
        String content = Files.readString(Path.of(PATH_TO_FILES + PATH_TO_CAR));
        CarSeedDto[] carSeedDtos = gson.fromJson(content, CarSeedDto[].class);
        Arrays.stream(carSeedDtos)
                .filter(validationUtil::isValid)
                .map(carSeedDto -> {
                    Car car = modelMapper.map(carSeedDto, Car.class);

                      car.setParts(partService.getRandomPart());
                    return car;

                })
                .forEach(carRepository::save);

    }

    @Override
    public Car getRandomCar() {
        long randomId = ThreadLocalRandom.current().nextLong(1,carRepository.count() + 1);
        return carRepository.findById(randomId).orElse(null);

    }

    @Override
    public List<CarMakeDto> findAllCarsByMake(String make) {
       return carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream().map(car -> modelMapper.map(car, CarMakeDto.class))
               .collect(Collectors.toList());
    }

    @Override
    public List<CarWithPartsDto> findAllCarsWithTheirParts() {
        return carRepository.findAll().stream()
                .map(car -> modelMapper.map(car,CarWithPartsDto.class))
                .collect(Collectors.toList());
    }
}
