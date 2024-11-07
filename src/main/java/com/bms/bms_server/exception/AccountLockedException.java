package com.bms.bms_server.exception;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(String message) {
        super(message);
    }
}
