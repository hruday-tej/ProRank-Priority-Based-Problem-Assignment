package dev.hruday.controller;


import dev.hruday.entity.UserEntity;
import dev.hruday.event.RegistrationCompleteEvent;
import dev.hruday.dto.UserDTO;
import dev.hruday.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/auth")
public class RegistrationController {

    private final UserService userService;

    private final ApplicationEventPublisher publisher;

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @GetMapping(path = "/hello")
    public String welcomeScreen(){
        return "Welcome to the server!!";
    }

//    TODO:
//    1. Resend Token
//    2. Send Email
//    3. Change Password

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO, final HttpServletRequest httpServletRequest){
        UserEntity userEntity = userService.registerUser(userDTO);
        if(userEntity == null){
            return new ResponseEntity<>("Username is Taken", HttpStatus.BAD_REQUEST);
        }
        publisher.publishEvent(new RegistrationCompleteEvent(userEntity, applicationUrl(httpServletRequest)));

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    @GetMapping(path = "/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        System.out.println("YESS< INSIDEEE!!");
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid"))return "User Verified Successully";
        return "Request Timed Out!";
    }

    @PostMapping(path = "/resetPassword")

    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+ httpServletRequest.getServerName()+":"+ httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
    }
}
