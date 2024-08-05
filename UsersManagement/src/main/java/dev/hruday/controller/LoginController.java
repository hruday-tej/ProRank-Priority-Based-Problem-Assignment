package dev.hruday.controller;

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
@RequestMapping(path = "/user")
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login_user(@RequestBody UserLoginDTO userLoginDTO) {
        UserEntity foundUser = userService.loginUser(userLoginDTO);
        if (foundUser == null || !passwordEncoder.matches(userLoginDTO.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect Email/Password");
        }

        return ResponseEntity.ok("Logged in successfully");
    }
}
