package dev.hruday.ProRank.Security.controller;

import dev.hruday.ProRank.Security.entity.User;
import dev.hruday.ProRank.Security.event.RegistrationCompleteEvent;
import dev.hruday.ProRank.Security.model.UserModel;
import dev.hruday.ProRank.Security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping(path = "/hello")
    public String welcomScreen(){
        return "Welcome to the server!!";
    }

//    TODO:
//    1. Resend Token
//    2. Send Email
//    3. Change Password

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest httpServletRequest){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(httpServletRequest)));

        return "Success";
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
