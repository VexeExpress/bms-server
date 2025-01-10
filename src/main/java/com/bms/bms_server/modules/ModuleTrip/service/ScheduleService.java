package com.bms.bms_server.modules.ModuleTrip.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;
import com.bms.bms_server.modules.ModuleRoute.repository.RouteRepository;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import com.bms.bms_server.modules.ModuleSeat.repositoty.SeatingChartRepository;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_Schedule;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RQ_Schedule;
import com.bms.bms_server.modules.ModuleTrip.entity.Schedule;
import com.bms.bms_server.modules.ModuleTrip.mapper.ScheduleMapper;
import com.bms.bms_server.modules.ModuleTrip.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    SeatingChartRepository seatingChartRepository;
    @Autowired
    RouteRepository routeRepository;

    // PB.07_US.04: Add new trip schedule
    public DTO_RP_Schedule addNewTripSchedule(DTO_RQ_Schedule dto) {
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        if (dto.getEmployeeId() == null) {
            throw new AppException(ErrorCode.INVALID_EMPLOYEE_ID);
        }
        if (dto.getValueRoute() == null) {
            throw new AppException(ErrorCode.INVALID_ROUTE_ID);
        }
        if (dto.getValueDateStart() == null) {
            throw new AppException(ErrorCode.INVALID_DATE_START);
        }
        if (dto.getValueTime() == null) {
            throw new AppException(ErrorCode.INVALID_TIME_START);
        }
        if (dto.getValueSeatChart() == null) {
            throw new AppException(ErrorCode.INVALID_SEAT_CHART_ID);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                        .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        SeatingChart chart = seatingChartRepository.findById(dto.getValueSeatChart())
                        .orElseThrow(() -> new AppException(ErrorCode.SEAT_CHART_NOT_FOUND));
        Route route = routeRepository.findById(dto.getValueRoute())
                        .orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));

        Schedule schedule = ScheduleMapper.toEntity(dto, company, employee, chart, route);
        Schedule saveschedule = scheduleRepository.save(schedule);
        return ScheduleMapper.toDTO(saveschedule);
    }

    // PB.07_US.07: Filter/Get list trip schedule
    public List<DTO_RP_Schedule> getListTripScheduleByCompanyId(Long companyId) {
        if (companyId == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        List<Schedule> schedules = scheduleRepository.findByCompanyId(companyId);
        return schedules.stream()
                .map(ScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DTO_RP_Schedule updateTripSchedule(Long id, DTO_RQ_Schedule dto) {
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        if (dto.getEmployeeId() == null) {
            throw new AppException(ErrorCode.INVALID_EMPLOYEE_ID);
        }
        if (dto.getValueRoute() == null) {
            throw new AppException(ErrorCode.INVALID_ROUTE_ID);
        }
        if (dto.getValueDateStart() == null) {
            throw new AppException(ErrorCode.INVALID_DATE_START);
        }
        if (dto.getValueTime() == null) {
            throw new AppException(ErrorCode.INVALID_TIME_START);
        }
        if (dto.getValueSeatChart() == null) {
            throw new AppException(ErrorCode.INVALID_SEAT_CHART_ID);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        SeatingChart chart = seatingChartRepository.findById(dto.getValueSeatChart())
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_CHART_NOT_FOUND));
        Route route = routeRepository.findById(dto.getValueRoute())
                .orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_SCHEDULE_NOT_FOUND));
        schedule.setChart(chart);
        schedule.setEmployee(employee);
        schedule.setRoute(route);
        schedule.setDateStart(dto.getValueDateStart());
        schedule.setDateEnd(dto.getValueDateEnd());
        schedule.setTime(dto.getValueTime());
        schedule.setEnableDateEnd(dto.getValueEnableDateEnd());
        scheduleRepository.save(schedule);
        return ScheduleMapper.toDTO(schedule);
    }

    public void deleteTripScheduleById(Long id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty()) {
            throw new AppException(ErrorCode.TRIP_SCHEDULE_NOT_FOUND);
        }
        scheduleRepository.deleteById(id);
    }
}
