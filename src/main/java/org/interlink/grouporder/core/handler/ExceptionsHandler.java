package org.interlink.grouporder.core.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public static ResponseEntity handleException(Exception e) {
        HttpStatus status = ExceptionsTypes.getStatus(e);

        return ResponseEntity.status(status).body(e.getMessage());
    }
}
