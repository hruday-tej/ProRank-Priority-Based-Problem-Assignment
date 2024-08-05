package dev.hruday.controller;

import dev.hruday.config.security.JWTGenerator;
import dev.hruday.dto.AuthReponseDTO;
import dev.hruday.dto.UserDTO;
import dev.hruday.dto.UserLoginDTO;
import dev.hruday.entity.UserEntity;
import dev.hruday.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/auth")
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthReponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
//        System.out.println("INSIDE LOGIN 0000000000000000");
        return userService.loginUser(userLoginDTO);
    }
}
