package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Ticket.TicketResponseDTO;
import com.bms.bms_server.dto.Ticket.UpdateTicketResquestDTO;
import com.bms.bms_server.dto.Ticket.UpdateTicketsRequestDTO;
import com.bms.bms_server.dto.Trip.TripInfoResponseDTO;
import com.bms.bms_server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "http://localhost:3000")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/list-ticket/{tripId}")
    public ResponseEntity<List<TicketResponseDTO>> getListTicketByTripId(@PathVariable Long tripId) {
        try {
            List<TicketResponseDTO> ticket = ticketService.getListTicketByTripId(tripId);
            return ResponseEntity.status(HttpStatus.OK).body(ticket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }
    @PutMapping("/update-tickets")
    public ResponseEntity<List<TicketResponseDTO>> updateTicket(@RequestBody UpdateTicketsRequestDTO request) {
        try {
            List<TicketResponseDTO> updatedTickets = ticketService.updateTickets(request.getTicketIds(), request.getCommonData());
            return ResponseEntity.status(HttpStatus.OK).body(updatedTickets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }
}
