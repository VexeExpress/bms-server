package com.bms.bms_server.config;

import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleEmployee.enums.Role;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(EmployeeRepository employeeRepository){
        return args -> {
            if (!employeeRepository.findByUsername("ADMIN_APP").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN_APP.name());
                Employee employee = Employee.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                employeeRepository.save(employee);
                log.warn("ADMIN APP VINAHOME: USERNAME: admin - PASSWORD: admin");
            }
        };
    }
}
