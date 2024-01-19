package com.tingweichen.applicationsystem.handler.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ApplicationSystemExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(ApplicationSystemExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handle(ResponseStatusException exception) {
//        log.error("User 輸入資料有誤。 (" + exception.getMessage() + ")");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("帳號或密碼有誤。 (" + exception.getMessage() + ")");
    }
}
