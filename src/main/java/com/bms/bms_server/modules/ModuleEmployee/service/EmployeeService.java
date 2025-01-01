package com.bms.bms_server.modules.ModuleEmployee.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_CreateEmployee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_EditEmployee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Assistant;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Driver;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Employee;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleEmployee.enums.Role;
import com.bms.bms_server.modules.ModuleEmployee.mapper.EmployeeMapper;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    PasswordEncoder passwordEncoder;

    // PB.01_US.01: Add New Employee
    public DTO_RP_Employee createEmployee(DTO_RQ_CreateEmployee dto) {
        System.out.println(dto);
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_USERNAME);
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_FULL_NAME);
        }
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        if (usernameExists(dto.getUsername(), dto.getCompanyId())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            throw new AppException(ErrorCode.PASSWORD_ENCRYPTION_FAILED);
        }
        dto.setPassword(hashedPassword);
        Employee employee = EmployeeMapper.toEntity(dto, company);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toDTO(savedEmployee);
    }

    // PB.01_US.02: Update Employee Information
    public DTO_RP_Employee updateEmployee(Long id, DTO_RQ_EditEmployee dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_FULL_NAME);
        }
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        employee.setFullName(dto.getFullName());
        employee.setStartDate(dto.getStartDate());
        employee.setBirthDate(dto.getBirthDate());
        employee.setGender(dto.getGender());
        employee.setEmail(dto.getEmail());
        employee.setAddress(dto.getAddress());
        employee.setStatus(dto.getStatus());
        employee.setRoles(dto.getRoles());
        employee.setPhone(dto.getPhone());
        employee.setAccessTms(dto.getAccessTms());
        employee.setAccessCms(dto.getAccessCms());
        employee.setAccessBms(dto.getAccessBms());

        employeeRepository.save(employee);

        return EmployeeMapper.toDTO(employee);
    }

    // PB.01_US.03: Remove Employee
    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        employeeRepository.deleteById(id);
    }

    // PB.01_US.04: Lock Account Employee
    public void lockAccountEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        Employee employee = optionalEmployee.get();
        employee.setStatus(false);
        employeeRepository.save(employee);
    }

    // PB.01_US.05: Change Password Account Employee
    public void changePassAccountEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        Employee employee = optionalEmployee.get();
        employee.setPassword(passwordEncoder.encode("12345678"));
        employeeRepository.save(employee);
    }

    // PB.01_US.06: Filter/Get Employee List
    public List<DTO_RP_Employee> getEmployeesByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // PB.01_US.07: Filter/Get List Employee Role Driver
    public List<DTO_RP_Driver> getDriverNameByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .filter(employee -> employee.getStatus() && employee.getRoles() != null && employee.getRoles().contains("DRIVER"))
                .map(EmployeeMapper::toDriverResponseDTO)
                .collect(Collectors.toList());
    }

    // PB.01_US.08: Filter/Get List Employee Role Assistant
    public List<DTO_RP_Assistant> getAssistantNameByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .filter(employee -> employee.getStatus() && employee.getRoles() != null && employee.getRoles().contains("ASSISTANT"))
                .map(EmployeeMapper::toAssistantResponseDTO)
                .collect(Collectors.toList());
    }


    public boolean usernameExists(String username, Long companyId) {
        return employeeRepository.existsByUsernameAndCompanyId(username, companyId);
    }










//
//    public List<DTO_RP_Employee> getEmployeesByCompanyId(Long companyId) {
//        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
//        return employees.stream()
//                .map(EmployeeMapper::toDTO)
//                .collect(Collectors.toList());
//    }


//    public List<DTO_RP_Employee> searchEmployeesByName(String fullName, Long companyId) {
//        List<Employee> employees = employeeRepository.findByFullNameContainingAndCompanyId(fullName, companyId);
//        return employees.stream()
//                .map(EmployeeMapper::toDTO)
//                .collect(Collectors.toList());
//    }




//    public List<DTO_RP_Employee> searchEmployeesByRole(Integer role, Long companyId) {
//        List<Employee> employees = employeeRepository.findByRoleAndCompanyId(role, companyId);
//        return employees.stream()
//                .map(EmployeeMapper::toDTO)
//                .collect(Collectors.toList());
//    }

//    public List<DTO_RP_Driver> getDriverByCompanyId(Long companyId) {
//        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
//        return employees.stream()
//                .filter(employee -> employee.getStatus() && employee.getRoles() == 2)
//                .map(EmployeeMapper::toDriverResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//
//    public List<DTO_RP_Assistant> getAssistantByCompanyId(Long companyId) {
//        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
//        return employees.stream()
//                .filter(employee -> employee.getStatus() && employee.getRoles() == 1)
//                .map(EmployeeMapper::toAssistantResponseDTO)
//                .collect(Collectors.toList());
//
//    }


}
