package com.helpdesk.helpdesk.controller;
import org.springframework.http.ResponseEntity;
import com.helpdesk.helpdesk.entity.Ticket;
import com.helpdesk.helpdesk.service.TicketService;
import org.springframework.web.bind.annotation.*;
import com.helpdesk.helpdesk.request.StatusRequest;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> createTicket(
            @RequestBody Ticket ticket,
            @RequestParam("userId") Long userId) {

        Ticket createdTicket = ticketService.createTicket(ticket, userId);

        if (createdTicket == null) {
            return ResponseEntity
                    .status(403)
                    .body("User not found or not allowed to create tickets");
        }

        return ResponseEntity
                .status(201)
                .body(createdTicket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(
            @RequestBody Ticket ticket,
            @PathVariable Long id,
            @RequestParam("userId") Long userId) {

        Ticket updatedTicket = ticketService.updateTicket(id, ticket, userId);

        if (updatedTicket == null) {
            return ResponseEntity
                    .status(403)
                    .body("Ticket not found or access denied");
        }

        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable Long id,
            @RequestParam("userId") Long userId) {

        boolean deleted = ticketService.deleteTicket(id, userId);

        if (!deleted) {
            return ResponseEntity
                    .status(403)
                    .body("Ticket not found or access denied");
        }

        return ResponseEntity.ok("Ticket deleted successfully");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam("userId") Long userId,
            @RequestBody StatusRequest request) {

        String status = request.getStatus();

        // Validate status input
        if (status == null ||
                (!status.equals("OPEN") &&
                        !status.equals("IN_PROGRESS") &&
                        !status.equals("RESOLVED") &&
                        !status.equals("CLOSED"))) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid status. Allowed: OPEN, IN_PROGRESS, RESOLVED, CLOSED");
        }

        Ticket updatedTicket = ticketService.updateStatus(
                id,
                status,
                userId
        );

        if (updatedTicket == null) {
            return ResponseEntity
                    .status(403)
                    .body("Ticket not found or status update not allowed");
        }

        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id) {

        Optional<Ticket> optionalTicket = ticketService.getTicketById(id);

        if (optionalTicket.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("Ticket not found");
        }

        return ResponseEntity.ok(optionalTicket.get());
    }
    @GetMapping
    public List<Ticket> getAllTickets(@RequestParam(required = false)String status){
        if(status!=null){
            return ticketService.getTicketsByStatus(status);
        }
        return ticketService.getAllTickets();
    }
    @GetMapping("/my")
    public List<Ticket> getMyTickets(
            @RequestParam Long userId) {

        return ticketService.getMyTickets(userId);
    }
    @GetMapping("/agent")
    public List<Ticket> getAgentTickets(
            @RequestParam(required = false) String status
    ) {
        if (status!=null) {
            return ticketService.getTicketsByStatus(status);
        }
        return ticketService.getAllTickets();
    }
}
