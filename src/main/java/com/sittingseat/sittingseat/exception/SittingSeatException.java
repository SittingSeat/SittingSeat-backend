package com.sittingseat.sittingseat.exception;

import lombok.Getter;

@Getter
public class SittingSeatException extends RuntimeException{

    private int status;
    private String message;
    private String code;

    public SittingSeatException(SittingSeatErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public SittingSeatException(SittingSeatErrorCode errorCode, Throwable cause) {
        super(cause);
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }
}
