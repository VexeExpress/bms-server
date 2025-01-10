package com.bms.bms_server.modules.ModuleTrip.mapper;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_Schedule;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RQ_Schedule;
import com.bms.bms_server.modules.ModuleTrip.entity.Schedule;

public class ScheduleMapper {


    public static DTO_RP_Schedule toDTO(Schedule saveschedule) {
        DTO_RP_Schedule dto = new DTO_RP_Schedule();
        dto.setId(saveschedule.getId());
        dto.setValueDateStart(saveschedule.getDateStart());
        dto.setValueDateEnd(saveschedule.getDateEnd());
        dto.setValueTime(saveschedule.getTime());
        dto.setCreatedAt(saveschedule.getCreatedAt());
        dto.setChart(saveschedule.getChart().getSeatChartName());
        dto.setEmployee(saveschedule.getEmployee().getFullName());
        dto.setRoute(saveschedule.getRoute().getRouteName());
        dto.setValueRoute(saveschedule.getRoute().getId());
        dto.setValueSeatChart(saveschedule.getChart().getId());
        dto.setValueEnableDateEnd(saveschedule.getEnableDateEnd());
        return dto;
    }

    public static Schedule toEntity(DTO_RQ_Schedule dto, Company company, Employee employee, SeatingChart chart, Route route) {
        Schedule schedule = new Schedule();
        schedule.setCompany(company);
        schedule.setEmployee(employee);
        schedule.setChart(chart);
        schedule.setRoute(route);
        schedule.setDateEnd(dto.getValueDateEnd());
        schedule.setDateStart(dto.getValueDateStart());
        schedule.setTime(dto.getValueTime());
        schedule.setEnableDateEnd(dto.getValueEnableDateEnd());
        return schedule;
    }
}
