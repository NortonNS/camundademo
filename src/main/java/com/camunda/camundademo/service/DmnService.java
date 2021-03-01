package com.camunda.camundademo.service;

public interface DmnService {
    void initDmnEngine();

    String decideDrink(String name, String timeOfDay);
}
