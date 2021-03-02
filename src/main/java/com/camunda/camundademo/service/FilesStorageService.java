package com.camunda.camundademo.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    void init();

    void save(MultipartFile file);

    Resource load(String filename);

    void deleteAll();

    Stream<Path> loadAll();

    Boolean verifyExcel(MultipartFile file) throws IOException, InvalidFormatException;
}