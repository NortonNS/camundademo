package com.camunda.camundademo.controller;

import com.camunda.camundademo.message.ResponseMessage;
import com.camunda.camundademo.model.FileInfo;
import com.camunda.camundademo.service.DmnTemplateService;
import com.camunda.camundademo.service.FilesStorageService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FilesController {

    @Autowired
    FilesStorageService storageService;

    @Autowired
    private DmnTemplateService dmnTemplateService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.verifyExcel(file);
            storageService.save(file);  // later this will save to the database for audit purposes.

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (InvalidFormatException e) {
            message = "File uploaded was not a valid Excel File, please verify";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/publish/{filename}")
    public ResponseEntity<ResponseMessage> publishFile(@PathVariable("filename") String filename) {
        String message = "";
        try {
            FileInputStream file = readExcelFile(filename);
            dmnTemplateService.convertDmnTemplate(file);

            message = "Rules uploaded successfully: " + filename;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload rules for " + filename + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    private FileInputStream readExcelFile(String filename) throws FileNotFoundException {
        return new FileInputStream(new File("./uploads/" + filename));
    }

}
