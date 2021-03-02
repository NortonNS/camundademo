package com.camunda.camundademo.exceptions;

import com.camunda.camundademo.message.ResponseMessage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleFileNotExcel(InvalidFormatException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File is not an excel file!"));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseMessage> handleDirectoryException(DirectoryCreationException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Directory cannot be created"));
    }
}