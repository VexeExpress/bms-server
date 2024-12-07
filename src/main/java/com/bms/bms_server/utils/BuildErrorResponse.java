package com.bms.bms_server.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BuildErrorResponse {
    public static ResponseEntity<?> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(Map.of(
                "error", message,
                "status", status.value()
        ));
    }
}
