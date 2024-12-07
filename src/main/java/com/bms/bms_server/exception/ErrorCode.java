package com.bms.bms_server.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống. Vui lòng thử lại"),
    OFFICE_EXISTED(1002, "Văn phòng đã tồn tại"),
    COMPANY_EXISTED(1003, "Công ty không tồn tại"),
    OFFICE_NAME_REQUIRED(1004, "Vui lòng cung cấp tên văn phòng")
    ;



    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
