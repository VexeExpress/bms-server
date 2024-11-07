package com.bms.bms_server.exception;

public class CompanyLockedException extends RuntimeException{
    public CompanyLockedException(String message) {
        super(message);
    }
}
