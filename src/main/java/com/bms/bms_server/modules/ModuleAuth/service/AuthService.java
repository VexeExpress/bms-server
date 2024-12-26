package com.bms.bms_server.modules.ModuleAuth.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleAuth.dto.*;
import com.bms.bms_server.modules.ModuleAuth.entity.InvalidatedToken;
import com.bms.bms_server.modules.ModuleAuth.repository.InvalidatedTokenRepository;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleAuth.entity.LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.mapper.LoginHistoryMapper;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.modules.ModuleAuth.repository.LoginHistoryRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    InvalidatedTokenRepository invalidatedTokenRepository;
    EmployeeRepository employeeRepository;

    LoginHistoryRepository loginHistoryRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;



    private final PasswordEncoder passwordEncoder;

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

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);


        Date expTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expTime.after(new Date()))){
            throw new AppException(ErrorCode.AUTHENTICATION_ERROR);
        }
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw  new AppException(ErrorCode.AUTHENTICATION_ERROR);
        }
        return signedJWT;
    }

    public DTO_RP_Introspect introspect(DTO_RQ_Introspect dto) throws JOSEException, ParseException {
        var token = dto.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return DTO_RP_Introspect.builder()
                .valid(isValid)
                .build();
    }

    public DTO_RP_Login login(DTO_RQ_Login dto) throws JOSEException {
        System.out.println(dto);
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

    public DTO_RP_Login refreshToken(DTO_RQ_RefreshToken request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);
        var jti = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expityTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        var username = signedJWT.getJWTClaimsSet().getSubject();
        var employee = employeeRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.AUTHENTICATION_ERROR)
        );
        var token = generateToken(employee);
        return DTO_RP_Login.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void  logout(DTO_RQ_Logout request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);
            String jti = signToken.getJWTClaimsSet().getJWTID();
            Date expityTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jti)
                    .expityTime(expityTime)
                    .build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception) {
            log.info("Token already expired");

        }
    }

    private String generateToken (Employee employee) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(employee.getUsername())
                .issuer("vinahome.online")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .claim("fullName", employee.getFullName())
                .claim("userId", employee.getId())
                .claim("companyId", employee.getCompany().getId())
                .claim("scope", buildScope(employee))
                .claim("companyName", employee.getCompany().getCompanyName())
                .jwtID(UUID.randomUUID().toString())
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

    public void register(DTO_RQ_Register dto) {
        System.out.println(dto);

    }
}
