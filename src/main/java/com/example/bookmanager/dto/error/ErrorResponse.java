package com.example.bookmanager.dto.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private List<String> errors;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(LocalDateTime timestamp, int status, List<String> errors, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
        this.path = path;
    }
}
