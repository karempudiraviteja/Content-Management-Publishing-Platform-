package com.micro.audit;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    private String username;

    private String role;

    private String action;

    private UUID contentId;

    private LocalDateTime timestamp;

    // getters and setters
}