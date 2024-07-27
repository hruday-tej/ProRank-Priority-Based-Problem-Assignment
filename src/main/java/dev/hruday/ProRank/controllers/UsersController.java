package dev.hruday.ProRank.controllers;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/user")
public class UsersController {
    @GetMapping(path = "/")
    public ResponseEntity<String> welcome_users(){
        return ResponseEntity.ok("Welcome users");
    }
}
