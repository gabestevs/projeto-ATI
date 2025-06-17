package com.example.supportsystem.support_system_backend.repository;

import com.example.supportsystem.support_system_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
