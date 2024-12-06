package com.bms.bms_server.modules.ModuleAuth.exception;

public class AccountLockedException extends RuntimeException{
    public AccountLockedException(String message) {
        super(message);
    }
}
