package com.ChatApp.Config;

import com.ChatApp.Exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<String> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.getMessage());
    }

    @ExceptionHandler(value = { IOException.class})
    @ResponseBody
    public ResponseEntity<String> handleException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY).body("Failed file transfer");
    }

    @ExceptionHandler(value = { Exception.class})
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        System.out.println(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).body("The server has ran into trouble");
    }
}