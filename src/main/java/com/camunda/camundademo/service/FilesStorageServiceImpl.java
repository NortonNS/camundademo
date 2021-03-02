package com.camunda.camundademo.service;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.camunda.camundademo.exceptions.DirectoryCreationException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new DirectoryCreationException("Could not load the files!",e);
        }
    }

    @Override
    public Boolean verifyExcel(MultipartFile file) throws IOException, InvalidFormatException {
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());
        Boolean fileExcel = isFileExcel(file);
        if (!Boolean.FALSE.equals(fileExcel)) {
            throw new InvalidFormatException("File not excel");
        }

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet firstSheet = workbook.getSheetAt(0);

        if (!"DrinkDecision".equals(firstSheet.getSheetName())) {
            throw new IOException("Invalid Sheet - Expecting DrinkDecision");
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private Boolean isFileExcel(MultipartFile file) {
        if (file != null && file.getOriginalFilename() != null) {
            return file.getOriginalFilename().toLowerCase().endsWith(".xlsx");
        }

        return false;
    }
}