package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Auth.LoginRequestDTO;
import com.bms.bms_server.dto.Auth.LoginResponseDTO;
import com.bms.bms_server.exception.AccountLockedException;
import com.bms.bms_server.exception.CompanyLockedException;
import com.bms.bms_server.exception.InvalidPasswordException;
import com.bms.bms_server.exception.UserNotFoundException;
import com.bms.bms_server.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        if (loginRequestDTO.getUsername() == null || loginRequestDTO.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400
        }
        if (loginRequestDTO.getPassword() == null || loginRequestDTO.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400
        }
        try {
            LoginResponseDTO response = authService.login(loginRequestDTO);
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

}
