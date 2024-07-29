package com.treative.epidemicsimulation.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class ResponseUtil {

    public static ResponseEntity<Map<String, String>> createErrorResponse(HttpStatus httpStatus, String errorName, String errorMess) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put(errorName, errorMess);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}
