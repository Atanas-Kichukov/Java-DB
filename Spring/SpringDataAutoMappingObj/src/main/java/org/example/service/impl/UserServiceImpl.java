package org.example.service.impl;

import org.example.model.dto.UserLoginDto;
import org.example.model.dto.UserRegisterDto;
import org.example.model.entity.Game;
import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.service.GameService;
import org.example.service.UserService;
import org.example.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;

    }


    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Passwords don't match. Enter a valid password");
            return;
        }
        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.violations(userRegisterDto);

        if (!violations.isEmpty()) {
            violations.stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }
        User user = modelMapper.map(userRegisterDto, User.class);
        userRepository.save(user);

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations
                = validationUtil.violations(userLoginDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }
        User user = userRepository.findByEmailAndPassword(userLoginDto.getEmail(), userLoginDto.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username/password");
            return;
        }
        System.out.println("Successfully logged in " + user.getFullName());
        loggedInUser = user;
    }

    @Override
    public void logout() {
        if (loggedInUser == null) {
            System.out.println("Cannot log out. No user was logged in");
        } else {
            System.out.printf("User %s successfully logged out%n", loggedInUser.getFullName());
            loggedInUser = null;
        }
    }

    @Override
    public boolean hasLoggedUser() {
        return loggedInUser != null;
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    @Override
    public void buyItem() {
        System.out.println("Successfully bought games");
        this.loggedInUser.getOrder().getGames().forEach(game->{
            loggedInUser.getGames().add(game);
            System.out.println(" -" + game.getTitle());
        });
        this.loggedInUser.setOrder(null);
        this.userRepository.save(this.loggedInUser);
    }

}
