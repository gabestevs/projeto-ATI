package com.example.supportsystem.support_system_backend.controller;

import com.example.supportsystem.support_system_backend.entity.Ticket;
import com.example.supportsystem.support_system_backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/client/{clientId}")
    public List<Ticket> getTicketsByClientId(@PathVariable Long clientId) {
        return ticketService.getTicketsByClientId(clientId);
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping("/{clientId}")
    public Ticket createTicket(@PathVariable Long clientId, @RequestBody Ticket ticket) {
        return ticketService.createTicket(clientId, ticket);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ticketService.updateTicket(id, ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
