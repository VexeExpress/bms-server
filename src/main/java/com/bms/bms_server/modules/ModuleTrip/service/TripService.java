package com.bms.bms_server.modules.ModuleTrip.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleTicket.entity.Ticket;
import com.bms.bms_server.modules.ModuleTicket.repository.TicketRepository;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_TripData;
import com.bms.bms_server.modules.ModuleTrip.entity.Schedule;
import com.bms.bms_server.modules.ModuleTrip.mapper.TripMapper;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.modules.ModuleRoute.repository.RouteRepository;
import com.bms.bms_server.modules.ModuleSeat.entity.Seat;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import com.bms.bms_server.modules.ModuleSeat.repositoty.SeatingChartRepository;
import com.bms.bms_server.modules.ModuleTrip.entity.Trip;
import com.bms.bms_server.modules.ModuleTrip.repository.ScheduleRepository;
import com.bms.bms_server.modules.ModuleTrip.repository.TripRepository;
import com.bms.bms_server.modules.ModuleVehicle.repository.VehicleRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {
    @Autowired
    TripRepository tripRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    SeatingChartRepository seatingChartRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    ScheduleRepository scheduleRepository;

    public List<DTO_RP_TripData> getListDataTrip(Long companyId, Long routeId, LocalDate date) {
        if (companyId == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        System.out.println("CompanyID: " + companyId);
        System.out.println("RouteID: " + routeId);
        System.out.println("Select date: " + date);

        List<Schedule> schedules = scheduleRepository.findByCompanyIdAndRouteIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(
                companyId, routeId, date, date);
        if (schedules.isEmpty()) {
            System.out.println("Không tìm thấy lịch chạy");

        } else {
            System.out.println("Tìm thấy lịch chạy");
            System.out.println(schedules);
        }

        List<Trip> trips = new ArrayList<>();

        for (Schedule schedule : schedules) {
            Trip existingTrip = tripRepository.findByRouteIdAndDateDepartureAndTimeDeparture(routeId, date, schedule.getTime());
            if (existingTrip == null) {
                Trip newTrip = Trip.builder()
                        .company(schedule.getCompany())
                        .route(schedule.getRoute())
                        .dateDeparture(date)
                        .timeDeparture(schedule.getTime())
                        .seatChart(schedule.getChart())
                        .schedule(schedule)
                        .build();

                // Lưu trip trước khi tạo vé
                try {
                    newTrip = tripRepository.save(newTrip);
                    createTicketsFromSeatingChart(newTrip);
                    trips.add(newTrip);
                    System.out.println("Đã tạo chuyến thành công: " + newTrip);
                } catch (Exception e) {
                    System.out.println("Lỗi khi tạo chuyến: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                trips.add(existingTrip);
            }
        }

        return trips.stream()
                .map(TripMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    public void createTicketsFromSeatingChart(Trip trip) {
        SeatingChart seatingChart = trip.getSchedule().getChart();
        System.out.println("Value seat chart: " + seatingChart);

        if (seatingChart == null || seatingChart.getSeats() == null || seatingChart.getSeats().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_SEAT_CHART_ID);
        }
        List<Ticket> tickets = new ArrayList<>();

        for (Seat seat: seatingChart.getSeats()) {
            Ticket ticket = Ticket.builder()
                    .company(trip.getCompany())
                    .trip(trip)
                    .ticketCode(seat.getSeatCode())
                    .ticketColumn(seat.getSeatColumn())
                    .ticketFloor(seat.getSeatFloor())
                    .ticketRow(seat.getSeatRow())
                    .ticketName(seat.getSeatName())
                    .ticketType(seat.getSeatType())
                    .bookingStatus(false)
                    .build();
            tickets.add(ticket);
        }

        try {
            ticketRepository.saveAll(tickets);
            System.out.println("Đã tạo vé thành công cho chuyến: " + trip.getId());
        } catch (Exception e) {
            System.out.println("Lỗi khi tạo vé: " + e.getMessage());
            e.printStackTrace();
        }
    }

//        List<Seat> seats = seatingChart.getSeats();
//        List<Ticket> tickets = new ArrayList<>();
//        for (Seat seat : seats) {
//            Ticket ticket = new Ticket();
//            ticket.setCompany(company);
//            ticket.setTrip(savedTrip);
////            ticket.setTicketFloor(seat.getFloor());
////            ticket.setTicketRow(seat.getRow());
////            ticket.setTicketColumn(seat.getColumn());
////            ticket.setTicketCode(seat.getSeatCode());
////            ticket.setTicketName(seat.getSeatName());
////            ticket.setTicketStatus(seat.getSeatStatus());
//            tickets.add(ticket);
//        }
//        ticketRepository.saveAll(tickets);
//        return TripMapper.toResponseDTO(savedTrip);
//    }
//
//
//
//    public List<DTO_RP_TripInfo> getTripsByCompanyIdAndRouteIdAndDate(Long companyId, Long routeId, LocalDate dateDeparture) {
//
//        System.out.println("---------------------------");
//        System.out.println("Company ID: " + companyId);
//        System.out.println("Route ID: " + routeId);
//        System.out.println("Date Departure: " + dateDeparture);
//
//        List<Trip> trips = tripRepository.findTripsByCompanyIdAndRouteIdAndDate(companyId, routeId, dateDeparture);
//        System.out.println("Retrieved Trips: " + trips);
//
//        return trips.stream()
//                .map(TripMapper::toResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//
//
//    public void deleteTripById(Long tripId) {
//        System.out.println("Delete Trip ID: " + tripId);
//        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));
//
//        // Xóa vé của chuyến
//        List<Ticket> tickets = ticketRepository.findAllByTripId(tripId);
//        System.out.println("Danh sách vé cần xóa:");
//        for (Ticket ticket : tickets) {
//            System.out.println("Ticket ID: " + ticket.getId() +
//                    ", Code: " + ticket.getTicketCode() +
//                    ", Name: " + ticket.getTicketName() +
//                    ", Status: " + ticket.getTicketStatus());
//        }
//        ticketRepository.deleteAll(tickets);
//
//
//        tripRepository.delete(trip);
//    }


}
