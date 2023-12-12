package com.socialnetwork.socialnetwork.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> errorResponseBuilder(List<String> errors){
        Map<String, Object> response = new HashMap<>();
        response.put("Errors", errors);
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
