package com.example.demo.exceptions;

import lombok.Data;

@Data
public class JikimException extends RuntimeException {
    private String message;

    public JikimException(String message) {
            this.message = message;
    }
}
