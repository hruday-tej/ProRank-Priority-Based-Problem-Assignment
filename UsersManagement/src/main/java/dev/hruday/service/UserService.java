package dev.hruday.service;


import dev.hruday.controller.AuthenticationResponse;
import dev.hruday.dto.UserLoginDTO;
import dev.hruday.entity.UserEntity;
import dev.hruday.dto.UserDTO;

public interface UserService {
    AuthenticationResponse registerUser(UserDTO userDTO);

    void saveVerificationTokenForUser(String token, UserEntity userEntity);

    String validateVerificationToken(String token);

    AuthenticationResponse loginUser(UserLoginDTO userLoginDTO);
}
