package com.warungposbespring.warungposbe.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpResponse {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj, Boolean success){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("time", new Date(System.currentTimeMillis()));
        map.put("success", success);

        return new ResponseEntity<Object>(map, status);
    }
}
