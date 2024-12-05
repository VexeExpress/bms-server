package com.bms.bms_server.service;

import com.bms.bms_server.dto.Auth.LoginHistoryResponseDTO;
import com.bms.bms_server.dto.Auth.LoginRequestDTO;
import com.bms.bms_server.dto.Auth.LoginResponseDTO;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.entity.LoginHistory;
import com.bms.bms_server.exception.AccountLockedException;
import com.bms.bms_server.exception.CompanyLockedException;
import com.bms.bms_server.exception.InvalidPasswordException;
import com.bms.bms_server.exception.UserNotFoundException;
import com.bms.bms_server.mapper.LoginHistoryMapper;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.repository.LoginHistoryRepository;
import com.bms.bms_server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    LoginHistoryRepository loginHistoryRepository;
    @Autowired
    JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        System.out.println(loginRequestDTO);
        Employee employee = employeeRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Tai khoan khong ton tai"));
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), employee.getPassword())){
            throw new InvalidPasswordException("Mat khau khong dung");
        }
        if (!employee.getStatus()) {
            throw new AccountLockedException("Tai khoan bi khoa");// 1: Hoạt động. 2: Ngưng hoạt động
        }
        if (!employee.getCompany().getStatus()) {
            throw new CompanyLockedException("Cong ty bi khoa");
        }
        LoginResponseDTO response = new LoginResponseDTO();
        response.setFullName(employee.getFullName());
        response.setCompanyName(employee.getCompany().getCompanyName());
        response.setCompanyId(employee.getCompany().getId());

        // Tạo token JWT
        try {
            String token = jwtUtil.generateToken(employee);
            response.setToken(token);
            System.out.println("Generated Token: " + token);
        } catch (Exception e) {
            System.err.println("Error generating token: " + e.getMessage());
            throw new RuntimeException("Error generating token");
        }


        // Save login history
        saveLoginHistory(loginRequestDTO, employee);
        
        return response;
    }

    private void saveLoginHistory(LoginRequestDTO loginRequestDTO, Employee employee) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setCompany(employee.getCompany());
        loginHistory.setEmployee(employee);
        loginHistory.setIpAddress(loginRequestDTO.getIpAddress());
        loginHistory.setBrowserName(loginRequestDTO.getBrowserName());
        loginHistory.setOperatingSystem(loginRequestDTO.getOperatingSystem());
        loginHistory.setTimeLogin(LocalTime.now());
        loginHistory.setDateLogin(LocalDate.now());
        loginHistoryRepository.save(loginHistory);
    }

    public List<LoginHistoryResponseDTO> getLoginHistoryByCompanyId(Long companyId) {
        List<LoginHistory> loginHistories = loginHistoryRepository.findByCompanyId(companyId);

        return loginHistories.stream()
                .map(LoginHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
