package com.example.supportsystem.support_system_backend.repository;

import com.example.supportsystem.support_system_backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByClientId(Long clientId);
}
