package com.example.supportsystem.support_system_backend.service;

import com.example.supportsystem.support_system_backend.entity.Client;
import com.example.supportsystem.support_system_backend.exception.ResourceNotFoundException;
import com.example.supportsystem.support_system_backend.repository.ClientRepository;
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
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("Client");
    }

    @Test
    void getAllClients_shouldReturnList() {
        List<Client> clients = Arrays.asList(client);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertEquals(1, result.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClientById_found_shouldReturnClient() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1L);

        assertEquals("Client", result.getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void getClientById_notFound_shouldThrow() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(1L));
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void createClient_shouldSaveAndReturn() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.createClient(client);

        assertEquals("Client", result.getName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void updateClient_found_shouldUpdateAndReturn() {
        Client updated = new Client();
        updated.setName("Updated Client");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.updateClient(1L, updated);

        assertEquals("Updated Client", result.getName());
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void updateClient_notFound_shouldThrow() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(1L, client));
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void deleteClient_found_shouldDelete() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).existsById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteClient_notFound_shouldThrow() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(1L));
        verify(clientRepository, times(1)).existsById(1L);
    }
}
