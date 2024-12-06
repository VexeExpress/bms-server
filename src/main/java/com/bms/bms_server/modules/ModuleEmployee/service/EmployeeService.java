package com.bms.bms_server.modules.ModuleEmployee.service;

import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_CreateEmployee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_EditEmployee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Assistant;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Driver;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Employee;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleEmployee.mapper.EmployeeMapper;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void createEmployee(DTO_RQ_CreateEmployee dto) {
        try {
            // Kiểm tra sự tồn tại của công ty
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new IllegalArgumentException("Công ty không tồn tại"));

            // Mã hóa mật khẩu
            String hashedPassword = passwordEncoder.encode(dto.getPassword());

            // Nếu mã hóa thất bại, sẽ ném lỗi IllegalArgumentException
            if (hashedPassword == null || hashedPassword.isEmpty()) {
                throw new IllegalArgumentException("Mã hóa mật khẩu không thành công");
            }

            // Cập nhật lại mật khẩu đã mã hóa vào DTO
            dto.setPassword(hashedPassword);

            // Chuyển đổi DTO sang entity và lưu vào cơ sở dữ liệu
            Employee employee = EmployeeMapper.toEntity(dto, company);
            employeeRepository.save(employee);

        } catch (IllegalArgumentException e) {
            // Lỗi do không tìm thấy công ty hoặc lỗi mã hóa mật khẩu
            throw new IllegalArgumentException("Lỗi xác thực dữ liệu");
        } catch (Exception e) {
            // Bắt các lỗi không xác định khác
            throw new RuntimeException("Đã xảy ra lỗi không xác định khi tạo nhân viên");
        }
    }


    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public void deleteEmployeeById(Long id) {
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
    public List<DTO_RP_Employee> getEmployeesByCompanyId(Long companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<DTO_RP_Employee> searchEmployeesByName(String fullName, Long companyId) {
        List<Employee> employees = employeeRepository.findByFullNameContainingAndCompanyId(fullName, companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DTO_RP_Employee> searchEmployeesByRole(Integer role, Long companyId) {
        List<Employee> employees = employeeRepository.findByRoleAndCompanyId(role, companyId);
        return employees.stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DTO_RP_Driver> getDriverByCompanyId(Long companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .filter(employee -> employee.getStatus() && employee.getRole() == 2)
                .map(EmployeeMapper::toDriverResponseDTO)
                .collect(Collectors.toList());
    }


    public List<DTO_RP_Assistant> getAssistantByCompanyId(Long companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream()
                .filter(employee -> employee.getStatus() && employee.getRole() == 1)
                .map(EmployeeMapper::toAssistantResponseDTO)
                .collect(Collectors.toList());

    }
}
