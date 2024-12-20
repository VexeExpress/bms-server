package com.bms.bms_server.modules.ModuleAuth.controller;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleAuth.dto.*;
import com.bms.bms_server.modules.ModuleAuth.service.AuthService;
import com.bms.bms_server.utils.ApiResponse;
import com.bms.bms_server.utils.CustomException;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.bms.bms_server.utils.BuildErrorResponse.buildErrorResponse;
import static java.util.stream.Stream.builder;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    @Autowired
    AuthService authService;



    @PostMapping("/token")
    ApiResponse<DTO_RP_Login> login(@RequestBody DTO_RQ_Login dto) throws JOSEException {
        var result = authService.login(dto);
        return ApiResponse.<DTO_RP_Login>builder()
                .code(1000)
                .message("Đăng nhập thành công")
                .result(result)
                .build();


    }
    @PostMapping("/introspect")
    ApiResponse<DTO_RP_Introspect> introspect (@RequestBody DTO_RQ_Introspect dto) throws ParseException, JOSEException {
        var result = authService.introspect(dto);
        return ApiResponse.<DTO_RP_Introspect>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout (@RequestBody DTO_RQ_Logout dto) throws ParseException, JOSEException {
        authService.logout(dto);
        return ApiResponse.<Void>builder()
                .code(1000)
                .build();
    }

    @PostMapping("/refresh-token")
    ApiResponse<DTO_RP_Login> refreshToken(@RequestBody DTO_RQ_RefreshToken dto) throws JOSEException, ParseException {
        var result = authService.refreshToken(dto);
        return ApiResponse.<DTO_RP_Login>builder()
                .code(1000)
                .result(result)
                .build();


    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody DTO_RQ_Login loginRequestDTO) {
//        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isEmpty()) {
//            throw new AppException(ErrorCode.REQUIRE_USERNAME);
//        }
//        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isEmpty()) {
//            throw new AppException(ErrorCode.REQUIRE_PASSWORD);
//        }
//        try {
//            DTO_RP_Login response = authService.login(loginRequestDTO);
//            return ResponseEntity.status(HttpStatus.OK).body(response); // 200
//        } catch (CustomException e) {
//            return buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
//        } catch (Exception e) {
//            return buildErrorResponse("Lỗi hệ thống, vui lòng thử lại sau", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    public DTO_RP_Introspect introspect(DTO_RQ_Introspect request) {
//        var token = request.getToken();
//    }
//    @PostMapping("/introspect")
//    public ResponseEntity<DTO_RP_Introspect> introspect(@RequestBody DTO_RQ_Introspect request) {
//        var result = authService.introspect(request);
//        return ApiResponse<DTO_RP_Introspect>builder().result(result).build();
//
//    }
    @GetMapping("/login-history/{companyId}")
    public ResponseEntity<List<DTO_RP_LoginHistory>> loginHistory(@PathVariable Long companyId) {
        try {
            List<DTO_RP_LoginHistory> loginHistories = authService.getLoginHistoryByCompanyId(companyId);
            return ResponseEntity.ok(loginHistories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

}
