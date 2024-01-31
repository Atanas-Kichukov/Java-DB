package org.example.service;

import org.example.model.dto.UserLoginDto;
import org.example.model.dto.UserRegisterDto;
import org.example.model.entity.Game;
import org.example.model.entity.User;

import java.util.Set;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logout();

    boolean hasLoggedUser();

    User getLoggedInUser();


    void buyItem();

}
