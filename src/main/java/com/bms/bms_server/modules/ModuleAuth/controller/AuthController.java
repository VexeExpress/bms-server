package com.bms.bms_server.modules.ModuleAuth.controller;

import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RP_LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RQ_Login;
import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RP_Login;
import com.bms.bms_server.modules.ModuleAuth.exception.AccountLockedException;
import com.bms.bms_server.modules.ModuleAuth.exception.CompanyLockedException;
import com.bms.bms_server.modules.ModuleAuth.exception.InvalidPasswordException;
import com.bms.bms_server.modules.ModuleAuth.exception.UserNotFoundException;
import com.bms.bms_server.modules.ModuleAuth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<DTO_RP_Login> login(@RequestBody DTO_RQ_Login loginRequestDTO) {
        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400
        }
        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400
        }
        try {
            DTO_RP_Login response = authService.login(loginRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response); // 200
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Tài khoản không tồn tại
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401: Mật khẩu không đúng
        } catch (AccountLockedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403: Tài khoản bị khoá
        } catch (CompanyLockedException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).build(); // 423: Công ty bị khoá
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
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
