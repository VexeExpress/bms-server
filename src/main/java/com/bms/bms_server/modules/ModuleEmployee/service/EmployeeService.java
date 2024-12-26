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


    public DTO_RP_Employee createEmployee(DTO_RQ_CreateEmployee dto) {
        System.out.println(dto);
        if (usernameExists(dto.getUsername(), dto.getCompanyId())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        try {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
            String hashedPassword = passwordEncoder.encode(dto.getPassword());
            if (hashedPassword == null || hashedPassword.isEmpty()) {
                throw new AppException(ErrorCode.PASSWORD_ENCRYPTION_FAILED);
            }
            dto.setPassword(hashedPassword);

//            HashSet<String> roles = new HashSet<>();
//
//            dto.setRoles(roles);

            Employee employee = EmployeeMapper.toEntity(dto, company);
            Employee savedEmployee = employeeRepository.save(employee);
            return EmployeeMapper.toDTO(savedEmployee);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    public boolean usernameExists(String username, Long companyId) {
        return employeeRepository.existsByUsernameAndCompanyId(username, companyId);
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        System.out.println("Delete Employee ID: " + id);
        employeeRepository.deleteById(id);
    }

    public DTO_RP_Employee updateEmployee(Long id, DTO_RQ_EditEmployee dto) {
        System.out.println("Update Employee ID: " + id);
        System.out.println("Update Employee: " + dto);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

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

        // Lưu cập nhật vào cơ sở dữ liệu
        employeeRepository.save(employee);

        // Chuyển đổi entity sang DTO để trả về
        return EmployeeMapper.toDTO(employee);
    }

    public void lockAccountEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Employee employee = optionalEmployee.get();
        employee.setStatus(false);
        employeeRepository.save(employee);
    }

    public void changePassAccountEmployee(Long id) {
        System.out.println("Change Pass Account Employee ID: " + id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Employee employee = optionalEmployee.get();
        employee.setPassword(passwordEncoder.encode("12345678"));
        employeeRepository.save(employee);
    }
//
//    public List<DTO_RP_Employee> getEmployeesByCompanyId(Long companyId) {
//        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
//        return employees.stream()
//                .map(EmployeeMapper::toDTO)
//                .collect(Collectors.toList());
//    }


    public List<DTO_RP_Employee> searchEmployeesByName(String fullName, Long companyId) {
        List<Employee> employees = employeeRepository.findByFullNameContainingAndCompanyId(fullName, companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DTO_RP_Employee> getEmployeesByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.COMPANY_NOT_EXIST);
        }
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        if (employees.isEmpty()) {
            throw new AppException(ErrorCode.NO_EMPLOYEES_FOUND);
        }
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }


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
