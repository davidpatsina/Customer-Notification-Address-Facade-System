package com.example.cnafs.exception;


import lombok.Getter;

@Getter
public class CnafsException extends RuntimeException{

    private int code;

    public CnafsException(String message) {
        super(message);
    }

    public CnafsException(String message, CnafsErrorCode winchErrorCode) {
        super(message);
        this.code = winchErrorCode.getCode();
    }
}
