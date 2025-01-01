package com.bms.bms_server.modules.ModuleRoute.controller;

import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_RouteName;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RQ_Route;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_Route;
import com.bms.bms_server.modules.ModuleRoute.service.RouteService;
import com.bms.bms_server.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RouteController {
    @Autowired
    RouteService routeService;

    // PB.04_US.01: Add new route
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Route> addNewRoute(@RequestBody DTO_RQ_Route dto) {
        var result = routeService.createRoute(dto);
        return ApiResponse.<DTO_RP_Route>builder()
                .code(1000)
                .message("Tạo tuyến mới thành công")
                .result(result)
                .build();
    }

    // PB.04_US.02: Filter/Get list route
    @GetMapping("/list-route/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<List<DTO_RP_Route>> getListRouteByCompanyId(@PathVariable Long companyId) {
        var result = routeService.getListRouteByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_Route>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }

    // PB.04_US.03: Filter/Get list route name
    @GetMapping("/list-route-name/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
    ApiResponse<List<DTO_RP_RouteName>> getRouteNameByCompanyId(@PathVariable Long companyId) {
        var result = routeService.getListRouteNameByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_RouteName>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }

    // PB.04_US.04: Update route information
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Route> updateRoute(@PathVariable Long id, @RequestBody DTO_RQ_Route dto) {
        var result = routeService.updateRoute(id, dto);
        return ApiResponse.<DTO_RP_Route>builder()
                .code(1000)
                .message("Cập nhật thông tin tuyến thành công")
                .result(result)
                .build();
    }

    // PB.04_US.05: Remove route
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRouteById(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xoá tuyến thành công")
                .build();
    }

    // PB.04_US.06: Move route display order
    @PostMapping("/move-order-top/{routeId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> moveRouteToTop(@PathVariable Long routeId) {
        routeService.moveRouteToTop(routeId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Di chuyển vị trí thành công")
                .build();
    }



//    public ResponseEntity<DTO_RP_Route> createRoute(@RequestBody DTO_RQ_Route dto) {
//        System.out.println(dto);
//        try {
//            DTO_RP_Route responseDto = routeService.createRoute(dto);
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
//    @GetMapping("/list-route/{companyId}")
//    public ResponseEntity<List<DTO_RP_Route>> getListRouteByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_Route> response = routeService.getListRouteByCompanyId(companyId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }
//    @PutMapping("/update/{routeId}")
//    public ResponseEntity<DTO_RP_Route> updateRoute(
//            @PathVariable Long routeId,
//            @RequestBody DTO_RQ_Route updatedData) {
//        try {
//            System.out.println(updatedData);
//            DTO_RP_Route updated = routeService.updateRoute(routeId, updatedData);
//            return ResponseEntity.ok(updated); // 200: Thành công
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Yêu cầu không hợp lệ
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
//        }
//    }
//    @DeleteMapping("/delete/{routeId}")
//    public ResponseEntity<Void> deleteRouteById(@PathVariable Long routeId) {
//        try {
//            routeService.deleteRouteById(routeId);
//            return ResponseEntity.noContent().build(); // 204: Xóa thành công
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy du lieu
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
//        }
//    }

//    @PostMapping("/move-order/{routeId}")
//    public ResponseEntity<Void> moveRouteToTop(@PathVariable Long routeId) {
//        try {
//            routeService.moveRouteToTop(routeId);
//            return ResponseEntity.ok().build(); // 200
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500
//        }
//    }
//
//    @GetMapping("/list-route-name/{companyId}")
//    public ResponseEntity<List<DTO_RP_RouteName>> getRouteNameByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_RouteName> response = routeService.getListRouteNameByCompanyId(companyId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }


}
