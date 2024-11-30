package com.bms.bms_server.service;

import com.bms.bms_server.dto.Ticket.TicketResponseDTO;
import com.bms.bms_server.dto.Ticket.UpdateTicketResquestDTO;
import com.bms.bms_server.entity.Employee;
import com.bms.bms_server.entity.Office;
import com.bms.bms_server.entity.Ticket;
import com.bms.bms_server.mapper.TicketMapper;
import com.bms.bms_server.repository.EmployeeRepository;
import com.bms.bms_server.repository.OfficeRepository;
import com.bms.bms_server.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public List<TicketResponseDTO> getListTicketByTripId(Long tripId) {
        List<Ticket> tickets = ticketRepository.findAllByTripId(tripId);
        return tickets.stream()
                .map(TicketMapper::toResponseDTO)
                .collect(Collectors.toList());
    }




    public List<TicketResponseDTO> updateTickets(List<Long> ticketIds, UpdateTicketResquestDTO commonData) {
        System.out.println(ticketIds);
        System.out.println(commonData);
        List<TicketResponseDTO> updatedTickets = new ArrayList<>();
        for (Long ticketId : ticketIds) {
            Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                ticket.setTicketPhone(commonData.getPhone());
                ticket.setCustomerName(commonData.getName());
                ticket.setTicketPointUp(commonData.getPickup());
                ticket.setTicketPointDown(commonData.getDropoff());
                ticket.setTicketNote(commonData.getNote());
                ticket.setPaymentType(commonData.getPaymentType());
                ticket.setTicketPrice(commonData.getPrice());
                Optional<Office> officeOptional = officeRepository.findById(commonData.getOfficeId());
                officeOptional.ifPresent(ticket::setOffice);
                Optional<Employee> employeeOptional = employeeRepository.findById(commonData.getEmployeeId());
                employeeOptional.ifPresent(ticket::setEmployee);

                // Lưu lại thay đổi
                Ticket updatedTicket = ticketRepository.save(ticket);
                updatedTickets.add(TicketMapper.toResponseDTO(updatedTicket));
            }
        }

        return updatedTickets;
    }
}
