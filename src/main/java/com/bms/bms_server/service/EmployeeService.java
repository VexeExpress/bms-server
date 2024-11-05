package com.bms.bms_server.service;

import com.bms.bms_server.dto.Employee.request.CreateEmployeeDTO;
import com.bms.bms_server.dto.Employee.response.EmployeeResponseDTO;
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
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        Employee employee = EmployeeMapper.toEntity(dto, company);

        employeeRepository.save(employee);
    }
//
//    public boolean existsById(Long id) {
//        return employeeRepository.existsById(id);
//    }
//
//    public void deleteEmployeeById(Long id) {
//        employeeRepository.deleteById(id);
//    }
//
//    public EmployeeResponseDTO updateEmployee(Long id, CreateEmployeeDTO dto) {
//        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//        if (optionalEmployee.isEmpty()) {
//            throw new IllegalArgumentException("Nhân viên không tồn tại.");
//        }
//        Employee existingEmployee = optionalEmployee.get();
//        existingEmployee.setUsername(dto.getUsername());
//        existingEmployee.setFullName(dto.getFullName());
//        existingEmployee.setRole(dto.getRole());
//        existingEmployee.setPhoneNumber(dto.getPhoneNumber());
//        existingEmployee.setAddress(dto.getAddress());
//        existingEmployee.setEmail(dto.getEmail());
//        existingEmployee.setIdCard(dto.getIdCard());
//        existingEmployee.setGender(dto.getGender());
//        existingEmployee.setBirthDate(dto.getBirthDate());
//        existingEmployee.setStatus(dto.getStatus());
//        existingEmployee.setExpirationDate(dto.getExpirationDate());
//        existingEmployee.setLicenseCategory(dto.getLicenseCategory());
//
//        Employee updatedEmployee = employeeRepository.save(existingEmployee);
//        return convertToResponseDTO(updatedEmployee);
//    }
//
//    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
//        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
//        responseDTO.setId(employee.getId());
//        responseDTO.setUsername(employee.getUsername());
//        responseDTO.setFullName(employee.getFullName());
//        responseDTO.setPhoneNumber(employee.getPhoneNumber());
//        responseDTO.setEmail(employee.getEmail());
//        responseDTO.setRole(employee.getRole());
//        responseDTO.setAddress(employee.getAddress());
//        responseDTO.setStatus(employee.getStatus());
//        responseDTO.setGender(employee.getGender());
//        responseDTO.setIdCard(employee.getIdCard());
//        responseDTO.setLicenseCategory(employee.getLicenseCategory());
//        responseDTO.setExpirationDate(employee.getExpirationDate());
//        responseDTO.setBirthDate(employee.getBirthDate());
//        return responseDTO;
//    }
//
//    public void lockAccountEmployee(Long id) {
//        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//        if (optionalEmployee.isEmpty()) {
//            throw new IllegalArgumentException("Nhân viên không tồn tại.");
//        }
//        Employee employee = optionalEmployee.get();
//        employee.setStatus(false);
//        employeeRepository.save(employee);
//    }
//
//    public void changePassAccountEmployee(Long id) {
//        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
//        if (optionalEmployee.isEmpty()) {
//            throw new IllegalArgumentException("Nhân viên không tồn tại.");
//        }
//        Employee employee = optionalEmployee.get();
//        employee.setPassword(passwordEncoder.encode("12345678"));
//        employeeRepository.save(employee);
//    }
//
//    public List<EmployeeResponseDTO> getEmployeesByCompanyId(Long companyId) {
//        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
//        return employees.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//    private EmployeeResponseDTO  convertToDto(Employee employee) {
//        EmployeeResponseDTO dto = new EmployeeResponseDTO();
//        dto.setId(employee.getId());
//        dto.setUsername(employee.getUsername());
//        dto.setStatus(employee.getStatus());
//        dto.setFullName(employee.getFullName());
//        dto.setPhoneNumber(employee.getPhoneNumber());
//        dto.setAddress(employee.getAddress());
//        dto.setEmail(employee.getEmail());
//        dto.setIdCard(employee.getIdCard());
//        dto.setGender(employee.getGender());
//        dto.setBirthDate(employee.getBirthDate());
//        dto.setLicenseCategory(employee.getLicenseCategory());
//        dto.setExpirationDate(employee.getExpirationDate());
//        return dto;
//    }
}
