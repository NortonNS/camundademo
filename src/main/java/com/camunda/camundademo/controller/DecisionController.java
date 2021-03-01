package com.camunda.camundademo.controller;

import com.camunda.camundademo.message.ResponseMessage;
import com.camunda.camundademo.service.DmnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/decision")
public class DecisionController {

    @Autowired
    private DmnService dmnService;

    @GetMapping("/drink/{name}/{timeOfDay}")
    public ResponseEntity<ResponseMessage> decideDrink(
            @PathVariable("name") String name,
            @PathVariable("timeOfDay") String timeOfDay) {
        String drink = dmnService.decideDrink(name, timeOfDay);
        return ResponseEntity.ok(
                new ResponseMessage(name + " wants a " + drink + " in the " + timeOfDay));
    }
}
