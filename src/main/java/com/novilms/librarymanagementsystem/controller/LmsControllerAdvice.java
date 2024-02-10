package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.exceptions.BusinessException;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class LmsControllerAdvice {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    protected ResponseEntity<String> handleConflict(MethodArgumentNotValidException mex) {
        return ResponseEntity.badRequest().body(mex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(", ")));
    }
    @ExceptionHandler({ RecordNotFoundException.class })
    protected ResponseEntity<String> handleConflict(RecordNotFoundException rex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rex.getMessage());
    }

    @ExceptionHandler({ BusinessException.class })
    protected ResponseEntity<String> handleConflict(BusinessException bex) {
        return ResponseEntity.badRequest().body(bex.getMessage());
    }
}
