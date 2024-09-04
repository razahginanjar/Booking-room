package com.enigma.challengebookingroom.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int status;
    private long  timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(String errorMessage, int status, long timestamp) {
        this.message = errorMessage;
        this.status = status;
        this.timestamp = timestamp;
    }

}
