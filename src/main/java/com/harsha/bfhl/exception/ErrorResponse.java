package com.harsha.bfhl.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty("is_success")
    private final boolean isSuccess;

    @JsonProperty("message")
    private final String message;

    public ErrorResponse(String message) {
        this.isSuccess = false;
        this.message = message;
    }

    public boolean isSuccess() { return isSuccess; }
    public String getMessage() { return message; }
}
