package com.bms.bms_server.utils;

import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {
    private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static long expirationTime = 3600000; // Thời gian hết hạn (1 giờ)
    // Tạo JWT Token
    public String generateToken(Employee employee) {
        return Jwts.builder()
                .setSubject(String.valueOf(employee.getFullName()))
                .claim("userId", employee.getId())
                .claim("companyId", employee.getCompany().getId())
                .claim("role", employee.getRole())
                .setIssuedAt(new Date())  // Set the issue time
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }
    // Phương thức giải mã và lấy thông tin từ token
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    // Phương thức lấy username từ token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Phương thức kiểm tra xem token có hết hạn hay không
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Phương thức xác minh tính hợp lệ của token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
