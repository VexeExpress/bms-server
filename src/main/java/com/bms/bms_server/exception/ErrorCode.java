package com.bms.bms_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {


    USERNAME_EXISTED(1005, "Tài khoản đã tồn tại", HttpStatus.CONFLICT),
    USER_NOT_EXISTED(1007, "Tài khoản không tồn tại", HttpStatus.NOT_FOUND),
    PASSWORD_IS_INCORRECT(1008, "Mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    ACCOUNT_HAS_BEEN_LOCKED(1009, "Tài khoản đã bị khoá", HttpStatus.FORBIDDEN),
    NO_ACCESS(1010, "Tài khoản không có quyền truy cập", HttpStatus.FORBIDDEN),
    COMPANY_HAS_BEEN_LOCKED(1011, "Công ty đã bị khoá", HttpStatus.FORBIDDEN),
    REQUIRE_USERNAME(1012, "Vui lòng cung cấp tài khoản", HttpStatus.BAD_REQUEST),
    REQUIRE_PASSWORD(1013, "Vui lòng cung cấp mật khẩu", HttpStatus.BAD_REQUEST),
    NO_EMPLOYEES_FOUND(1014, "Không tìm thấy danh sách nhân viên", HttpStatus.NOT_FOUND),

    // App
    UNCATEGORIZED_EXCEPTION(999, "Lỗi hệ thống. Vui lòng thử lại", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTHENTICATION_ERROR(888, "Tài khoản không có quyền truy cập", HttpStatus.FORBIDDEN),

    // Company
    INVALID_COMPANY_ID(777, "Dữ liệu công ty không hợp lệ", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_EXIST(8888, "Dữ liệu công ty không tồn tại", HttpStatus.NOT_FOUND),

    // PB.01: Employee
    INVALID_USERNAME(101, "Vui lòng cung cấp tài khoản", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(102, "Vui lòng cung cấp mật khẩu", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS(103,"Tài khoản đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_FULL_NAME(104, "Vui lòng cung cấp tên người dùng", HttpStatus.BAD_REQUEST),
    PASSWORD_ENCRYPTION_FAILED(105, "Lỗi mã hoá mật khẩu. Vui lòng thử lại", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(106, "Dữ liệu người dùng không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_EMPLOYEE_ID(107, "Vui lòng cung cấp thông tin người dùng", HttpStatus.BAD_REQUEST),

    // PB.02: Office
    INVALID_OFFICE_NAME(201, "Vui lòng cung cấp tên văn phòng", HttpStatus.BAD_REQUEST),
    OFFICE_ALREADY_EXISTED(202, "Văn phòng đã tồn tại", HttpStatus.CONFLICT),
    OFFICE_NOT_FOUND(203, "Dữ liệu văn phòng không tồn tại", HttpStatus.NOT_FOUND),

    // PB.03: Vehicle
    INVALID_LICENSE_PLATE(301, "Vui lòng cung cấp biển số xe", HttpStatus.BAD_REQUEST),
    VEHICLE_ALREADY_EXISTED(302, "Phương tiện đã tồn tại", HttpStatus.CONFLICT),
    VEHICLE_NOT_FOUND(303, "Dữ liệu phương tiện không tồn tại", HttpStatus.NOT_FOUND),


    OFFICE_EXISTED(403, "Văn phòng đã tồn tại", HttpStatus.CONFLICT),
    OFFICE_NAME_REQUIRED(304, "Vui lòng cung cấp tên văn phòng", HttpStatus.BAD_REQUEST),

    VEHICLES_NOT_FOUND(201, "Không tìm thấy danh sách phương tiện", HttpStatus.NOT_FOUND),


    VEHICLE_LICENSE_PLATE_REQUIRED(204, "Vui lòng cung cấp biển số xe", HttpStatus.BAD_REQUEST),


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
