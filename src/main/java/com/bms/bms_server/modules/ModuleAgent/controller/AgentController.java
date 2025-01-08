package com.bms.bms_server.modules.ModuleAgent.controller;

import com.bms.bms_server.modules.ModuleAgent.dto.*;
import com.bms.bms_server.modules.ModuleAgent.service.AgentService;
import com.bms.bms_server.utils.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AgentController {
    @Autowired
    AgentService agentService;

    // PB.05_US.01: Add new agent
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Agent> createAgent(@RequestBody DTO_RQ_Agent dto) {
        var result = agentService.createAgent(dto);
        return ApiResponse.<DTO_RP_Agent>builder()
                .code(1000)
                .message("Tạo đại lý thành công")
                .result(result)
                .build();
    }

    // PB.05_US.02: Update agent information
    @PutMapping("/update/{agentId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Agent> updateAgent(@PathVariable Long agentId, @RequestBody DTO_RQ_Agent dto) {
        var result = agentService.updateAgent(agentId, dto);
        return ApiResponse.<DTO_RP_Agent>builder()
                .code(1000)
                .message("Cập nhật thông tin đại lý thành công")
                .result(result)
                .build();
    }

    // PB.05_US.03: Remove agent
    @DeleteMapping("/delete/{agentId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> deleteAgent(@PathVariable Long agentId) {
        agentService.deleteAgentById(agentId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xoá đại lý thành công")
                .build();
    }

    // PB.05_US.04: Filter/Get list agent
    @GetMapping("/list-agent/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<List<DTO_RP_Agent>> getListAgentByCompanyId(@PathVariable("companyId") Long companyId) {
        var result = agentService.getListAgentByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_Agent>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }

    // PB.05_US.05: Filter/Get list agent name
    @GetMapping("/list-agent-name/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<List<DTO_RP_AgentName>> getListAgentNameByCompanyId(@PathVariable("companyId") Long companyId) {
        var result = agentService.getListAgentNameByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_AgentName>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }

    // PB.05_US.06: Assign agent to specific routes
    @PutMapping("/assign-agent/{agentId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_AssignAgentToRoute> assignAgentToRoutes(@PathVariable("agentId") Long agentId, @RequestBody DTO_RQ_AssignAgentToRoute dto) throws JsonProcessingException {
        var result = agentService.assignAgentToRoutes(agentId, dto);
        return ApiResponse.<DTO_RP_AssignAgentToRoute>builder()
                .code(1000)
                .message("Cấp quyền truy cập thành công")
                .result(result)
                .build();
    }

    // PB.05_US.07: Enable agent account
    @PutMapping("/enable-account/{agentId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> enableAgentAccount(@PathVariable("agentId") Long agentId) {
        agentService.enableAgentAccount(agentId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Kích hoạt tài khoản đại lý thành công")
                .build();
    }
    
    // PB.05_US.08: Disable agent account
    @PutMapping("/disable-account/{agentId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> disableAgentAccount(@PathVariable("agentId") Long agentId) {
        agentService.disableAgentAccount(agentId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Ngưng kích hoạt tài khoản đại lý thành công")
                .build();
    }

    // PB.05_US.09: Notifications for agents

//    @PostMapping("/create")
//    public ResponseEntity<DTO_RP_Agent> createAgent(@RequestBody DTO_RQ_Agent dto) {
//        System.out.println(dto);
//        try {
//            DTO_RP_Agent responseDto = agentService.createAgent(dto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 201
//        } catch (IllegalArgumentException e) {
//            if (e.getMessage().contains("required")) {
//                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build(); // 422: Du lieu dau vao loi
//            } else if (e.getMessage().contains("already exists")) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Du lieu da ton tai trong cong ty
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Du lieu vao loi
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Cong ty khong ton tai
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }
//    @GetMapping("/list-agent/{companyId}")
//    public ResponseEntity<List<DTO_RP_Agent>> getListAgentByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_Agent> response = agentService.getListAgentByCompanyId(companyId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }
//    @PutMapping("/update/{agentId}")
//    public ResponseEntity<DTO_RP_Agent> updateAgent(
//            @PathVariable Long agentId,
//            @RequestBody DTO_RQ_Agent updatedData) {
//        try {
//            System.out.println(updatedData);
//            DTO_RP_Agent updated = agentService.updateAgent(agentId, updatedData);
//            return ResponseEntity.ok(updated); // 200: Thành công
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Yêu cầu không hợp lệ
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
//        }
//    }
//    @DeleteMapping("/delete/{agentId}")
//    public ResponseEntity<Void> deleteAgentById(@PathVariable Long agentId) {
//        try {
//            agentService.deleteAgentById(agentId);
//            return ResponseEntity.noContent().build(); // 204: Xóa thành công
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
//        }
//    }
}
