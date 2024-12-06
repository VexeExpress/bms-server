package com.bms.bms_server.modules.ModuleAuth.exception;

public class CompanyLockedException extends RuntimeException{
    public CompanyLockedException(String message) {
        super(message);
    }
}
