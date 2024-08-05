package dev.hruday.service;


import dev.hruday.dto.UserLoginDTO;
import dev.hruday.entity.UserEntity;
import dev.hruday.dto.UserDTO;

public interface UserService {
    UserEntity registerUser(UserDTO userDTO);

    void saveVerificationTokenForUser(String token, UserEntity userEntity);

    String validateVerificationToken(String token);

    UserEntity loginUser(UserLoginDTO userLoginDTO);
}
