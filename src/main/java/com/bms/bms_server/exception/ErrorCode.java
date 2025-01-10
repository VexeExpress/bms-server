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

    // PB.04: Route
    INVALID_ROUTE_NAME(401, "Vui lòng cung cấp tên tuyến", HttpStatus.BAD_REQUEST),
    INVALID_DISPLAY_PRICE(402, "Vui lòng cung cấp giá tuyến cơ bản", HttpStatus.BAD_REQUEST),
    ROUTE_ALREADY_EXISTED(403, "Tuyến đã tồn tại", HttpStatus.CONFLICT),
    ROUTE_NOT_FOUND(404, "Dữ liệu tuyến không tồn tại", HttpStatus.NOT_FOUND),
    ROUTE_ALREADY_AT_TOP(405, "Tuyến đường đã ở vị trí cao nhất, không thể di chuyển lên.", HttpStatus.BAD_REQUEST),
    INCONSISTENT_DISPLAY_ORDER(406, "Thứ tự hiển thị của các tuyến đường không đồng bộ.", HttpStatus.CONFLICT),
    INVALID_ROUTE_ID(407, "Vui lòng cung cấp thông tin tuyến", HttpStatus.BAD_REQUEST),

    // PB.05: Agent
    INVALID_NAME_AGENT(501, "Vui lòng cung cấp tên đại lý", HttpStatus.BAD_REQUEST),
    AGENT_ALREADY_EXISTED(502, "Đại lý đã tồn tại", HttpStatus.CONFLICT),
    AGENT_NOT_FOUND(503, "Dữ liệu đại lý không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_USERNAME_AGENT(504, "Vui lòng cung cấp tài khoản đại lý", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_AGENT(505, "Vui lòng cung cấp mật khẩu đại lý", HttpStatus.BAD_REQUEST),
    USERNAME_AGENT_ALREADY_EXISTED(506, "Tài khoản đại lý đã tồn tại", HttpStatus.CONFLICT),

    // PB.06: Seat
    INVALID_NAME_SEAT_CHART(601, "Vui lòng cung cấp tên sơ đồ", HttpStatus.BAD_REQUEST),
    SEAT_CHART_ALREADY_EXISTED(602, "Sơ đồ đã tồn tại", HttpStatus.CONFLICT),
    SEAT_CHART_NOT_FOUND(603, "Dữ liệu sơ đồ không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_SEAT_CHART_ID(604, "Vui lòng cung cấp thông tin sơ đồ", HttpStatus.BAD_REQUEST),

    // PB.07: Trip
    INVALID_DATE_START(701, "Vui lòng cung cấp ngày bắt đầu", HttpStatus.BAD_REQUEST),
    INVALID_TIME_START(702, "Vui lòng cung cấp thời gian", HttpStatus.BAD_REQUEST),
    TRIP_SCHEDULE_NOT_FOUND(703, "Dữ liệu lịch chạy không tồn tại", HttpStatus.NOT_FOUND),
    // PB.08: Auth

    // PB.09: Cargo

    // PB.10: Ticket

    // PB.11: Acceptance

    // PB.12: Point

    // PB.13: Payment


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
