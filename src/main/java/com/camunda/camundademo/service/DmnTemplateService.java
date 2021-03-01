package com.camunda.camundademo.service;

import java.io.IOException;
import java.io.InputStream;

public interface DmnTemplateService {
    void convertDmnTemplate(InputStream inputStream) throws IOException;
}
