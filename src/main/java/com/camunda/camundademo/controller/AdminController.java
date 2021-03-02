package com.camunda.camundademo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @RequestMapping("/test")
    public String returnTest() {
        return "Testing";
    }

    @RequestMapping("/")
    public String returnDefault() {
        return "Default";
    }

}
