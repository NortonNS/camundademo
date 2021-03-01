package com.camunda.camundademo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class BasicResponseMessage {
    private String message;
}
