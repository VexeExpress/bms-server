package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Route.RouteNameResponseDTO;
import com.bms.bms_server.dto.Route.RouteRequestDTO;
import com.bms.bms_server.dto.Route.RouteResponseDTO;
import com.bms.bms_server.dto.Vehicle.VehicleRequestDTO;
import com.bms.bms_server.dto.Vehicle.VehicleResponseDTO;
import com.bms.bms_server.service.RouteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
@CrossOrigin(origins = "http://localhost:3000")
public class RouteController {
    @Autowired
    RouteService routeService;

    @PostMapping("/create")
    public ResponseEntity<RouteResponseDTO> createRoute(@RequestBody RouteRequestDTO dto) {
        System.out.println(dto);
        try {
            RouteResponseDTO responseDto = routeService.createRoute(dto);
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
    @GetMapping("/list-route/{companyId}")
    public ResponseEntity<List<RouteResponseDTO>> getListRouteByCompanyId(@PathVariable Long companyId) {
        try {
            List<RouteResponseDTO> response = routeService.getListRouteByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
    @PutMapping("/update/{routeId}")
    public ResponseEntity<RouteResponseDTO> updateRoute(
            @PathVariable Long routeId,
            @RequestBody RouteRequestDTO updatedData) {
        try {
            System.out.println(updatedData);
            RouteResponseDTO updated = routeService.updateRoute(routeId, updatedData);
            return ResponseEntity.ok(updated); // 200: Thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Yêu cầu không hợp lệ
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
    @DeleteMapping("/delete/{routeId}")
    public ResponseEntity<Void> deleteRouteById(@PathVariable Long routeId) {
        try {
            routeService.deleteRouteById(routeId);
            return ResponseEntity.noContent().build(); // 204: Xóa thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

    @PostMapping("/move-order/{routeId}")
    public ResponseEntity<Void> moveRouteToTop(@PathVariable Long routeId) {
        try {
            routeService.moveRouteToTop(routeId);
            return ResponseEntity.ok().build(); // 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500
        }
    }

    @GetMapping("/list-route-name/{companyId}")
    public ResponseEntity<List<RouteNameResponseDTO>> getRouteNameByCompanyId(@PathVariable Long companyId) {
        try {
            List<RouteNameResponseDTO> response = routeService.getListRouteNameByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }


}
