package dev.hruday.service;


import dev.hruday.dto.AuthReponseDTO;
import dev.hruday.dto.UserLoginDTO;
import dev.hruday.entity.UserEntity;
import dev.hruday.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserEntity registerUser(UserDTO userDTO);

    void saveVerificationTokenForUser(String token, UserEntity userEntity);

    String validateVerificationToken(String token);

    ResponseEntity<AuthReponseDTO> loginUser(UserLoginDTO userLoginDTO);
}
