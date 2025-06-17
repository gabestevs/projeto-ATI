package com.example.supportsystem.support_system_backend.service;

import com.example.supportsystem.support_system_backend.entity.Client;
import com.example.supportsystem.support_system_backend.entity.Ticket;
import com.example.supportsystem.support_system_backend.exception.ResourceNotFoundException;
import com.example.supportsystem.support_system_backend.repository.ClientRepository;
import com.example.supportsystem.support_system_backend.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private TicketService ticketService;

    private Client client;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("Client");

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setClient(client);
        ticket.setCategory("Technical");
        ticket.setContent("Issue description");
        ticket.setStatus("Open");
    }

    @Test
    void getAllTickets_shouldReturnList() {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.getAllTickets();

        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void getTicketsByClientId_shouldReturnList() {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketRepository.findByClientId(1L)).thenReturn(tickets);

        List<Ticket> result = ticketService.getTicketsByClientId(1L);

        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findByClientId(1L);
    }

    @Test
    void getTicketById_found_shouldReturnTicket() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicketById(1L);

        assertEquals("Technical", result.getCategory());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void getTicketById_notFound_shouldThrow() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.getTicketById(1L));
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void createTicket_clientFound_shouldSaveAndReturn() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.createTicket(1L, ticket);

        assertEquals("Technical", result.getCategory());
        verify(clientRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void createTicket_clientNotFound_shouldThrow() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.createTicket(1L, ticket));
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void updateTicket_found_shouldUpdateAndReturn() {
        Ticket updated = new Ticket();
        updated.setCategory("Billing");
        updated.setContent("Updated issue");
        updated.setStatus("Closed");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.updateTicket(1L, updated);

        assertEquals("Billing", result.getCategory());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void updateTicket_notFound_shouldThrow() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.updateTicket(1L, ticket));
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTicket_found_shouldDelete() {
        when(ticketRepository.existsById(1L)).thenReturn(true);

        ticketService.deleteTicket(1L);

        verify(ticketRepository, times(1)).existsById(1L);
        verify(ticketRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTicket_notFound_shouldThrow() {
        when(ticketRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> ticketService.deleteTicket(1L));
        verify(ticketRepository, times(1)).existsById(1L);
    }
}
