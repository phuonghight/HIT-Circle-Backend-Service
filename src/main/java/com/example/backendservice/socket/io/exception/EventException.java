package com.example.backendservice.socket.io.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventException extends RuntimeException {

    private String message;

    public EventException(String message) {
        super(message);
        this.message = message;
    }

}
