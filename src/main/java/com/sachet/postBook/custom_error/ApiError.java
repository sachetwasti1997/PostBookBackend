package com.sachet.postBook.custom_error;

import java.util.Date;
import java.util.Map;

public class ApiError {

    private long timeStamp = new Date().getTime();

    private int statusCode;

    private String message;

    private Map<String , String > validationErrors;

    public ApiError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiError() {
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
