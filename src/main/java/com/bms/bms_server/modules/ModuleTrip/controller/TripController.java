package com.bms.bms_server.modules.ModuleTrip.controller;

import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_TripData;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_TripInfo;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RQ_Trip;
import com.bms.bms_server.modules.ModuleTrip.service.TripService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trip")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TripController {
    @Autowired
    TripService tripService;

    @GetMapping("/list-data-trip/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
    ApiResponse<List<DTO_RP_TripData>> getListDataTrip(@PathVariable("companyId") Long companyId, @RequestParam("route") Long routeId, @RequestParam("date") LocalDate date) {
        var result = tripService.getListDataTrip(companyId, routeId, date);
        return ApiResponse.<List<DTO_RP_TripData>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }

//    @PostMapping("/create")
//    public ResponseEntity<DTO_RP_TripInfo> createTrip(@RequestBody DTO_RQ_Trip tripRequestDTO) {
//        try {
//            DTO_RP_TripInfo newTrip = tripService.createTrip(tripRequestDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(newTrip); // Trả về Trip mới và mã HTTP 201
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Nếu có lỗi thì trả về mã HTTP 400
//        }
//    }
//    @GetMapping("/search-trip")
//    public ResponseEntity<List<DTO_RP_TripInfo>> getTripsByCompanyIdAndRouteIdAndDate(
//            @RequestParam Long companyId,
//            @RequestParam Long routeId,
//            @RequestParam LocalDate dateDeparture) {
//        try {
//            List<DTO_RP_TripInfo> trips = tripService.getTripsByCompanyIdAndRouteIdAndDate(companyId, routeId, dateDeparture);
//            System.out.println(trips);
//            return ResponseEntity.status(HttpStatus.OK).body(trips);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @DeleteMapping("/delete/{tripId}")
//    public ResponseEntity<Void> deleteTripById(@PathVariable Long tripId) {
//        try {
//            tripService.deleteTripById(tripId);
//            return ResponseEntity.noContent().build();
//        }  catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
