package com.bms.bms_server.modules.ModuleTicket.controller;

import com.bms.bms_server.modules.ModuleTicket.dto.DTO_RP_Ticket;
import com.bms.bms_server.modules.ModuleTicket.dto.TicketResponseDTO;
import com.bms.bms_server.modules.ModuleTicket.dto.UpdateTicketsRequestDTO;
import com.bms.bms_server.modules.ModuleTicket.service.TicketService;
import com.bms.bms_server.utils.ApiResponse;
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
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/list-ticket/{tripId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
    ApiResponse<List<DTO_RP_Ticket>> getListTicketByTripId(@PathVariable Long tripId) {
        var result = ticketService.getListTicketByTripId(tripId);
        return ApiResponse.<List<DTO_RP_Ticket>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }


//    @GetMapping("/list-ticket/{tripId}")
//    public ResponseEntity<List<TicketResponseDTO>> getListTicketByTripId(@PathVariable Long tripId) {
//        try {
//            List<TicketResponseDTO> ticket = ticketService.getListTicketByTripId(tripId);
//            return ResponseEntity.status(HttpStatus.OK).body(ticket);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
//        }
//    }
//    @PutMapping("/update-tickets")
//    public ResponseEntity<List<TicketResponseDTO>> updateTicket(@RequestBody UpdateTicketsRequestDTO request) {
//        try {
//            List<TicketResponseDTO> updatedTickets = ticketService.updateTickets(request.getTicketIds(), request.getCommonData());
//            return ResponseEntity.status(HttpStatus.OK).body(updatedTickets);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
//        }
//    }
}
