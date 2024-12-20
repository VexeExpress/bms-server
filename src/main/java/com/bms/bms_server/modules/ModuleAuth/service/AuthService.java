package com.bms.bms_server.modules.ModuleAuth.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleAuth.dto.*;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleAuth.entity.LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.mapper.LoginHistoryMapper;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.modules.ModuleAuth.repository.LoginHistoryRepository;
import com.bms.bms_server.utils.CustomException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
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

//    public DTO_RP_Login login(DTO_RQ_Login loginRequestDTO) {
//        System.out.println(loginRequestDTO);
//        Employee employee = employeeRepository.findByUsername(loginRequestDTO.getUsername())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), employee.getPassword())){
//            throw new AppException(ErrorCode.PASSWORD_IS_INCORRECT);
//        }
//        if (!employee.getStatus()) {
//            throw new AppException(ErrorCode.ACCOUNT_HAS_BEEN_LOCKED);
//        }
//        if (employee.getRole() == 1 || employee.getRole() == 2) {
//            throw new AppException(ErrorCode.NO_ACCESS);
//        }
//        if (!employee.getCompany().getStatus()) {
//            throw new AppException(ErrorCode.COMPANY_HAS_BEEN_LOCKED);
//        }
//        if (!employee.getAccessBms()) {
//            throw new AppException(ErrorCode.NO_ACCESS);
//        }
//        DTO_RP_Login response = new DTO_RP_Login();
//        try {
//            String token = generateToken(employee.getFullName(), employee.getId().toString(), employee.getCompany().getId().toString(), employee.getRole().toString(), employee.getCompany().getCompanyName());
//
//            response.setToken(token);
//            System.out.println("Generated Token: " + token);
//        } catch (Exception e) {
//            System.err.println("Error generating token: " + e.getMessage());
//            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
//        }
//        saveLoginHistory(loginRequestDTO, employee);
//        return response;
//    }

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

    public DTO_RP_Introspect introspect(DTO_RQ_Introspect dto) throws JOSEException, ParseException {
        var token = dto.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        signedJWT.verify(verifier);

        Date expTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return DTO_RP_Introspect.builder()
                .valid(verified && expTime.after(new Date()))
                .build();
    }


    public DTO_RP_Login login(DTO_RQ_Login dto) throws JOSEException {
        var employee = employeeRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(dto.getPassword(), employee.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.PASSWORD_IS_INCORRECT);
        }
        var token = generateToken(employee);
        return DTO_RP_Login.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
    private String generateToken (Employee employee) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(employee.getUsername())
                .issuer("vinahome.online")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("fullName", employee.getFullName())
                .claim("userId", employee.getId())
                .claim("companyId", employee.getCompany().getId())
                .claim("scope", buildScope(employee))
                .claim("companyName", employee.getCompany().getCompanyName())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        return jwsObject.serialize();
    }
    private String buildScope(Employee employee) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(employee.getRoles())) {
            employee.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
