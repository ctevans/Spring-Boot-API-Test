package com.codingtest.demo.utils.Errors;

public class ErrorResponse {
    public ErrorResponse(String message) {
        this.message = message;
    }

    private String message;

    //<editor-fold desc="Getters/Setters">
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //</editor-fold>
}
