package org.example.service;

import org.example.model.Dtos.UserSoldDto;
import org.example.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void seedUsers() throws IOException;

    User findRandomUser();

    List<UserSoldDto> findAllUsersWithMoreThanOneSoldProducts();
}
