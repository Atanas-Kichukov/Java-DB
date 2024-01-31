package org.example.service.impl;

import com.google.gson.Gson;
import org.example.constants.GlobalConstants;
import org.example.model.Dtos.UserSeedDto;
import org.example.model.Dtos.UserSoldDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
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

import static org.example.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private static final String USERS_FILE_NAME = "users.json";

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {
        if(userRepository.count()>0){
            return;
        }
        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME)),//четем от файла с gson
                UserSeedDto[].class))//даваме [].class защотото в масив, може да се првовери във файла за да се провери дали е масив
                .filter(validationUtil::isValid)//използваме валидатора за да видим дали информацията е валидна ако не е просто ще махна невалидната
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))//преобразуваме от ДТО-то към вече ЕНТИТИТО user
                .forEach(userRepository::save);//запазваме в базата
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1,userRepository.count() + 1);//защотот е екслузив не взима последното Id и с repository.count взимаме броя на взички записани резултати в базата
        return userRepository.findById(randomId).orElse(null);

    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts() {

        return userRepository.findAllUsersWithMoreThanOneSoldProductsOrderByLastNameThenFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }
}
