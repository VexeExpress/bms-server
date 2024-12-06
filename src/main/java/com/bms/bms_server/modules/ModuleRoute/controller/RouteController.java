package com.bms.bms_server.modules.ModuleRoute.controller;

import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_RouteName;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RQ_Route;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_Route;
import com.bms.bms_server.modules.ModuleRoute.service.RouteService;
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
    public ResponseEntity<DTO_RP_Route> createRoute(@RequestBody DTO_RQ_Route dto) {
        System.out.println(dto);
        try {
            DTO_RP_Route responseDto = routeService.createRoute(dto);
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
    public ResponseEntity<List<DTO_RP_Route>> getListRouteByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_Route> response = routeService.getListRouteByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
    @PutMapping("/update/{routeId}")
    public ResponseEntity<DTO_RP_Route> updateRoute(
            @PathVariable Long routeId,
            @RequestBody DTO_RQ_Route updatedData) {
        try {
            System.out.println(updatedData);
            DTO_RP_Route updated = routeService.updateRoute(routeId, updatedData);
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
    public ResponseEntity<List<DTO_RP_RouteName>> getRouteNameByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_RouteName> response = routeService.getListRouteNameByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }


}
