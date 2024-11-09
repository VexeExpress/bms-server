package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Agent.AgentRequestDTO;
import com.bms.bms_server.dto.Agent.AgentResponseDTO;
import com.bms.bms_server.dto.Route.RouteRequestDTO;
import com.bms.bms_server.dto.Route.RouteResponseDTO;
import com.bms.bms_server.service.AgentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent")
@CrossOrigin(origins = "http://localhost:3000")
public class AgentController {
    @Autowired
    AgentService agentService;

    @PostMapping("/create")
    public ResponseEntity<AgentResponseDTO> createAgent(@RequestBody AgentRequestDTO dto) {
        System.out.println(dto);
        try {
            AgentResponseDTO responseDto = agentService.createAgent(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 201
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("required")) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build(); // 422: Du lieu dau vao loi
            } else if (e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Du lieu da ton tai trong cong ty
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Du lieu vao loi
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Cong ty khong ton tai
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
    @GetMapping("/list-agent/{companyId}")
    public ResponseEntity<List<AgentResponseDTO>> getListAgentByCompanyId(@PathVariable Long companyId) {
        try {
            List<AgentResponseDTO> response = agentService.getListAgentByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
    @PutMapping("/update/{agentId}")
    public ResponseEntity<AgentResponseDTO> updateAgent(
            @PathVariable Long agentId,
            @RequestBody AgentRequestDTO updatedData) {
        try {
            System.out.println(updatedData);
            AgentResponseDTO updated = agentService.updateAgent(agentId, updatedData);
            return ResponseEntity.ok(updated); // 200: Thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Yêu cầu không hợp lệ
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
    @DeleteMapping("/delete/{agentId}")
    public ResponseEntity<Void> deleteAgentById(@PathVariable Long agentId) {
        try {
            agentService.deleteAgentById(agentId);
            return ResponseEntity.noContent().build(); // 204: Xóa thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
}
