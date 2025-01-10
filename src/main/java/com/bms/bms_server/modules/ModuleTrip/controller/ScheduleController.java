package com.bms.bms_server.modules.ModuleTrip.controller;

import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_Schedule;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RQ_Schedule;
import com.bms.bms_server.modules.ModuleTrip.service.ScheduleService;
import com.bms.bms_server.utils.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    // PB.07_US.04: Add new trip schedule
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Schedule> addNewTripSchedule(@RequestBody DTO_RQ_Schedule dto) {
        var result = scheduleService.addNewTripSchedule(dto);
        return ApiResponse.<DTO_RP_Schedule>builder()
                .code(1000)
                .message("Tạo lịch chạy thành công")
                .result(result)
                .build();
    }

    // PB.07_US.05: Update trip schedule
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Schedule> updateTripSchedule(@PathVariable Long id, @RequestBody DTO_RQ_Schedule dto) {
        var result = scheduleService.updateTripSchedule(id, dto);
        return ApiResponse.<DTO_RP_Schedule>builder()
                .code(1000)
                .message("Cập nhật thông tin lịch chạy thành công")
                .result(result)
                .build();
    }

    // PB.07_US.06: Remove trip schedule
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> deleteTripSchedule(@PathVariable Long id) {
        scheduleService.deleteTripScheduleById(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xoá lịch chạy thành công")
                .build();
    }
    
    // PB.07_US.07: Filter/Get list trip schedule
    @GetMapping("/list-schedule/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<List<DTO_RP_Schedule>> getListTripScheduleByCompanyId(@PathVariable Long companyId) {
        var result = scheduleService.getListTripScheduleByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_Schedule>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }


}
