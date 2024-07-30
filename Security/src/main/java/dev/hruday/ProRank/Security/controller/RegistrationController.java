package dev.hruday.ProRank.Security.controller;

import dev.hruday.ProRank.Security.entity.User;
import dev.hruday.ProRank.Security.event.RegistrationCompleteEvent;
import dev.hruday.ProRank.Security.model.UserModel;
import dev.hruday.ProRank.Security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel){
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, "url"));

        return "Success";
    }
}
