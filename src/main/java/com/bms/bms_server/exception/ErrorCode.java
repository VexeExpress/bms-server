package com.bms.bms_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống. Vui lòng thử lại", HttpStatus.INTERNAL_SERVER_ERROR),
    COMPANY_NOT_EXIST(8888, "Công ty không tồn tại", HttpStatus.NOT_FOUND),
    AUTHENTICATION_ERROR(7777, "Lỗi xác thực", HttpStatus.FORBIDDEN),

    USERNAME_EXISTED(1005, "Tài khoản đã tồn tại", HttpStatus.CONFLICT),
    PASSWORD_ENCRYPTION_FAILED(1006, "Mã hoá mật khẩu thất bại", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXISTED(1007, "Tài khoản không tồn tại", HttpStatus.NOT_FOUND),
    PASSWORD_IS_INCORRECT(1008, "Mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    ACCOUNT_HAS_BEEN_LOCKED(1009, "Tài khoản đã bị khoá", HttpStatus.FORBIDDEN),
    NO_ACCESS(1010, "Tài khoản không có quyền truy cập", HttpStatus.FORBIDDEN),
    COMPANY_HAS_BEEN_LOCKED(1011, "Công ty đã bị khoá", HttpStatus.FORBIDDEN),
    REQUIRE_USERNAME(1012, "Vui lòng cung cấp tài khoản", HttpStatus.BAD_REQUEST),
    REQUIRE_PASSWORD(1013, "Vui lòng cung cấp mật khẩu", HttpStatus.BAD_REQUEST),
    NO_EMPLOYEES_FOUND(1014, "Không tìm thấy danh sách nhân viên", HttpStatus.NOT_FOUND),



    OFFICES_NOT_FOUND(101, "Không tìm thấy danh sách văn phòng", HttpStatus.NOT_FOUND),
    OFFICE_NOT_FOUND(102, "Không tìm thấy thông tin văn phòng", HttpStatus.NOT_FOUND),
    OFFICE_EXISTED(103, "Văn phòng đã tồn tại", HttpStatus.CONFLICT),
    OFFICE_NAME_REQUIRED(104, "Vui lòng cung cấp tên văn phòng", HttpStatus.BAD_REQUEST),

    ;
//        2xx: Thành công.
//        3xx: Chuyển hướng.
//        4xx: Lỗi từ phía máy khách.
//        5xx: Lỗi từ phía máy chủ.



    ErrorCode(Integer code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private Integer code;
    private String message;
    private HttpStatusCode statusCode;


}
