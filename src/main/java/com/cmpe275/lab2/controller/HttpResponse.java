package com.cmpe275.lab2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public enum  HttpResponse {
    OK(200, "Ok"),
    BAD_REQUEST(400,"400 Bad Request"),
    NOT_FOUND(404,"404 Not Found");

    private int code;
    private String message;

    HttpResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseEntity response() {
        return ResponseEntity.status(code).body(message);
    }

    public ResponseEntity response(String message) {
        return ResponseEntity.status(code).body(message);
    }
}
