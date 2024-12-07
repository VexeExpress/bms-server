package com.bms.bms_server.modules.ModuleAuth.service;

import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RP_LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RQ_Login;
import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RP_Login;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleAuth.entity.LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.mapper.LoginHistoryMapper;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.modules.ModuleAuth.repository.LoginHistoryRepository;
import com.bms.bms_server.utils.CustomException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    LoginHistoryRepository loginHistoryRepository;

    @NonFinal
    protected  static  final String SIGNER_KEY = "s90XEMKELEqFK80jVCWxwRpjsE70aSiEISEk9bEeNScd7FfvLRyLvKowIQgVtAAW\n";

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public DTO_RP_Login login(DTO_RQ_Login loginRequestDTO) {
        System.out.println(loginRequestDTO);
        Employee employee = employeeRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new CustomException("Tài khoản không tồn tại"));
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), employee.getPassword())){
            throw new CustomException("Mật khẩu không chính xác");
        }
        if (!employee.getStatus()) {
            throw new CustomException("Tài khoản đã bị khoá");
        }
        if (employee.getRole() == 1 || employee.getRole() == 2) {
            throw new CustomException("Tài khoản không có quyền truy cập");
        }
        if (!employee.getCompany().getStatus()) {
            throw new CustomException("Công ty đã bị khoá");
        }
        if (!employee.getAccessBms()) {
            throw new CustomException("Tài khoản không có quyền truy cập");
        }
        DTO_RP_Login response = new DTO_RP_Login();
        try {
            String token = generateToken(employee.getFullName(), employee.getId().toString(), employee.getCompany().getId().toString(), employee.getRole().toString(), employee.getCompany().getCompanyName());

            response.setToken(token);
            System.out.println("Generated Token: " + token);
        } catch (Exception e) {
            System.err.println("Error generating token: " + e.getMessage());
            throw new RuntimeException("Lỗi hệ thống");
        }
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

    private String generateToken (String fullName, String userId, String companyId, String role, String companyName) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(fullName)
                .issuer("vinahome.online")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("userId", userId)
                .claim("companyId", companyId)
                .claim("role", role)
                .claim("companyName", companyName)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    }
}
