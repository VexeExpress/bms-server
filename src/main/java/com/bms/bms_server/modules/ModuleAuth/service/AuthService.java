package com.bms.bms_server.modules.ModuleAuth.service;

import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RP_LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RQ_Login;
import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RP_Login;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleAuth.entity.LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.exception.AccountLockedException;
import com.bms.bms_server.modules.ModuleAuth.exception.CompanyLockedException;
import com.bms.bms_server.modules.ModuleAuth.exception.InvalidPasswordException;
import com.bms.bms_server.modules.ModuleAuth.exception.UserNotFoundException;
import com.bms.bms_server.modules.ModuleAuth.mapper.LoginHistoryMapper;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.modules.ModuleAuth.repository.LoginHistoryRepository;
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

    public DTO_RP_Login login(DTO_RQ_Login loginRequestDTO) {
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
        DTO_RP_Login response = new DTO_RP_Login();
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

    private void saveLoginHistory(DTO_RQ_Login loginRequestDTO, Employee employee) {
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

    public List<DTO_RP_LoginHistory> getLoginHistoryByCompanyId(Long companyId) {
        List<LoginHistory> loginHistories = loginHistoryRepository.findByCompanyId(companyId);

        return loginHistories.stream()
                .map(LoginHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
