package dev.hruday.ProRank.controllers;

import dev.hruday.ProRank.dto.UserDTO;
import dev.hruday.ProRank.services.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/user")
public class UsersController {

//    @Autowired
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        System.out.println("THIS IS CONSTRUCTOR zzzz");
        this.userService = userService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> welcome_users(){
        return ResponseEntity.ok("Welcome users");
    }

    @PostMapping(path = "/createUser")
    public UserDTO create_user(@RequestBody UserDTO userDTO){
        System.out.println("WE HIT THE RIGHT ENDPOINT");
        System.out.println("WE HIT THE RIGHT ENDPOINT");
        System.out.println("WE HIT THE RIGHT ENDPOINT");
        System.out.println("WE HIT THE RIGHT ENDPOINT");
         return this.userService.createNewUser(userDTO);
    }

    @GetMapping(path = "/{id}")
    public UserDTO get_user_by_id(@PathVariable("id") Long userId){
        return this.userService.getUserById(userId);
    }
}
