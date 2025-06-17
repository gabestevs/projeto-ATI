package com.example.supportsystem.support_system_backend.service;

import com.example.supportsystem.support_system_backend.entity.Client;
import com.example.supportsystem.support_system_backend.entity.Ticket;
import com.example.supportsystem.support_system_backend.exception.ResourceNotFoundException;
import com.example.supportsystem.support_system_backend.repository.ClientRepository;
import com.example.supportsystem.support_system_backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ClientRepository clientRepository) {
        this.ticketRepository = ticketRepository;
        this.clientRepository = clientRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByClientId(Long clientId) {
        return ticketRepository.findByClientId(clientId);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
    }

    public Ticket createTicket(Long clientId, Ticket ticket) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + clientId));
        ticket.setClient(client);
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id, Ticket updatedTicket) {
        Ticket existing = getTicketById(id);
        existing.setCategory(updatedTicket.getCategory());
        existing.setContent(updatedTicket.getContent());
        existing.setStatus(updatedTicket.getStatus());
        return ticketRepository.save(existing);
    }

    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found with id: " + id);
        }
        ticketRepository.deleteById(id);
    }
}
