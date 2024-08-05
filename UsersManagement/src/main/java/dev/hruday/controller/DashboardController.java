package dev.hruday.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/dashboard")
public class DashboardController {

    @GetMapping
    public String dashboard() {
        return "Welcome to the Dashboard!";
    }
}
