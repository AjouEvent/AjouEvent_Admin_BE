package com.ajouevent.admin.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(400)
                .body(Map.of(
                        "code", errorCode.getCode(),
                        "message", errorCode.getMessage()
                ));
    }
}
