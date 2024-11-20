package com.bms.bms_server.service;

import com.bms.bms_server.dto.Employee.request.CreateEmployeeDTO;
import com.bms.bms_server.dto.Employee.request.EditEmployeeDTO;
import com.bms.bms_server.dto.Employee.response.EmployeeDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Employee;
import com.bms.bms_server.mapper.EmployeeMapper;
import com.bms.bms_server.repository.CompanyRepository;
import com.bms.bms_server.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean usernameExists(String username, Long companyId) {
        return employeeRepository.existsByUsernameAndCompanyId(username, companyId);
    }

    @Transactional
    public void createEmployee(CreateEmployeeDTO dto) {
        System.out.println(dto);
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        Employee employee = EmployeeMapper.toEntity(dto, company);

        employeeRepository.save(employee);
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public void deleteEmployeeById(Long id) {
        System.out.println("Delete Employee ID: " + id);
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO updateEmployee(Long id, EditEmployeeDTO dto) {
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
        employee.setRole(dto.getRole());
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
        System.out.println("Lock Account Employee ID: " + id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Nhân viên không tồn tại.");
        }
        Employee employee = optionalEmployee.get();
        employee.setStatus(false);
        employeeRepository.save(employee);
    }

    public void changePassAccountEmployee(Long id) {
        System.out.println("Change Pass Account Employee ID: " + id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Nhân viên không tồn tại.");
        }
        Employee employee = optionalEmployee.get();
        employee.setPassword(passwordEncoder.encode("12345678"));
        employeeRepository.save(employee);
    }
//
    public List<EmployeeDTO> getEmployeesByCompanyId(Long companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<EmployeeDTO> searchEmployeesByName(String fullName, Long companyId) {
        List<Employee> employees = employeeRepository.findByFullNameContainingAndCompanyId(fullName, companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> searchEmployeesByRole(Integer role, Long companyId) {
        List<Employee> employees = employeeRepository.findByRoleAndCompanyId(role, companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
