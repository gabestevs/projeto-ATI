package com.example.supportsystem.support_system_backend.controller;

import com.example.supportsystem.support_system_backend.entity.Client;
import com.example.supportsystem.support_system_backend.entity.Ticket;
import com.example.supportsystem.support_system_backend.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllTickets_shouldReturnList() throws Exception {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketService.getAllTickets()).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Technical"));

        verify(ticketService, times(1)).getAllTickets();
    }

    @Test
    void getTicketsByClientId_shouldReturnList() throws Exception {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketService.getTicketsByClientId(1L)).thenReturn(tickets);

        mockMvc.perform(get("/api/tickets/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Technical"));

        verify(ticketService, times(1)).getTicketsByClientId(1L);
    }

    @Test
    void getTicketById_shouldReturnTicket() throws Exception {
        when(ticketService.getTicketById(1L)).thenReturn(ticket);

        mockMvc.perform(get("/api/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Technical"));

        verify(ticketService, times(1)).getTicketById(1L);
    }

    @Test
    void createTicket_shouldReturnCreatedTicket() throws Exception {
        when(ticketService.createTicket(eq(1L), any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Technical"));

        verify(ticketService, times(1)).createTicket(eq(1L), any(Ticket.class));
    }

    @Test
    void updateTicket_shouldReturnUpdatedTicket() throws Exception {
        when(ticketService.updateTicket(eq(1L), any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(put("/api/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Technical"));

        verify(ticketService, times(1)).updateTicket(eq(1L), any(Ticket.class));
    }

    @Test
    void deleteTicket_shouldReturnNoContent() throws Exception {
        doNothing().when(ticketService).deleteTicket(1L);

        mockMvc.perform(delete("/api/tickets/1"))
                .andExpect(status().isNoContent());

        verify(ticketService, times(1)).deleteTicket(1L);
    }
}