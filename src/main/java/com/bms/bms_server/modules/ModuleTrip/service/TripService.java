package com.bms.bms_server.modules.ModuleTrip.service;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleTicket.entity.Ticket;
import com.bms.bms_server.modules.ModuleTicket.repository.TicketRepository;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_TripInfo;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RQ_Trip;
import com.bms.bms_server.modules.ModuleTrip.mapper.TripMapper;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleEmployee.repository.EmployeeRepository;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;
import com.bms.bms_server.modules.ModuleRoute.repository.RouteRepository;
import com.bms.bms_server.modules.ModuleSeat.entity.Seat;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import com.bms.bms_server.modules.ModuleSeat.repositoty.SeatingChartRepository;
import com.bms.bms_server.modules.ModuleTrip.entity.Trip;
import com.bms.bms_server.modules.ModuleTrip.repository.TripRepository;
import com.bms.bms_server.modules.ModuleVehicle.entity.Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    @Transactional
    public DTO_RP_TripInfo createTrip(DTO_RQ_Trip tripRequestDTO) {
        System.out.println(tripRequestDTO);
        Company company = companyRepository.findById(tripRequestDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        Route route = routeRepository.findById(tripRequestDTO.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        Vehicle vehicle = vehicleRepository.findById(tripRequestDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        SeatingChart seatingChart = seatingChartRepository.findById(tripRequestDTO.getSeatChartId())
                .orElseThrow(() -> new RuntimeException("SeatChart not found"));

        List<Employee> drivers = employeeRepository.findAllById(tripRequestDTO.getDriverIds());
        List<Employee> assistants = employeeRepository.findAllById(tripRequestDTO.getAssistantIds());

        Trip trip = new Trip();
        trip.setCompany(company);
        trip.setRoute(route);
        trip.setVehicle(vehicle);
        trip.setSeatChart(seatingChart);
        trip.setDrivers(drivers); // Thêm tài xế
        trip.setAssistant(assistants); // Thêm phụ xe
        trip.setTimeDeparture(tripRequestDTO.getTimeDeparture());
        trip.setDateDeparture(tripRequestDTO.getDateDeparture());
        trip.setNote(tripRequestDTO.getNote());


        Trip savedTrip = tripRepository.save(trip);

        List<Seat> seats = seatingChart.getSeats();
        List<Ticket> tickets = new ArrayList<>();
        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setCompany(company);
            ticket.setTrip(savedTrip);
//            ticket.setTicketFloor(seat.getFloor());
//            ticket.setTicketRow(seat.getRow());
//            ticket.setTicketColumn(seat.getColumn());
//            ticket.setTicketCode(seat.getSeatCode());
//            ticket.setTicketName(seat.getSeatName());
//            ticket.setTicketStatus(seat.getSeatStatus());
            tickets.add(ticket);
        }
        ticketRepository.saveAll(tickets);
        return TripMapper.toResponseDTO(savedTrip);
    }



    public List<DTO_RP_TripInfo> getTripsByCompanyIdAndRouteIdAndDate(Long companyId, Long routeId, LocalDate dateDeparture) {

        System.out.println("---------------------------");
        System.out.println("Company ID: " + companyId);
        System.out.println("Route ID: " + routeId);
        System.out.println("Date Departure: " + dateDeparture);

        List<Trip> trips = tripRepository.findTripsByCompanyIdAndRouteIdAndDate(companyId, routeId, dateDeparture);
        System.out.println("Retrieved Trips: " + trips);

        return trips.stream()
                .map(TripMapper::toResponseDTO)
                .collect(Collectors.toList());
    }



    public void deleteTripById(Long tripId) {
        System.out.println("Delete Trip ID: " + tripId);
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new RuntimeException("Trip not found"));

        // Xóa vé của chuyến
        List<Ticket> tickets = ticketRepository.findAllByTripId(tripId);
        System.out.println("Danh sách vé cần xóa:");
        for (Ticket ticket : tickets) {
            System.out.println("Ticket ID: " + ticket.getId() +
                    ", Code: " + ticket.getTicketCode() +
                    ", Name: " + ticket.getTicketName() +
                    ", Status: " + ticket.getTicketStatus());
        }
        ticketRepository.deleteAll(tickets);


        tripRepository.delete(trip);
    }
}
