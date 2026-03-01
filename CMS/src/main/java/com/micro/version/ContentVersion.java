package com.micro.version;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "content_version")
public class ContentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID contentId;

    private int versionNumber;

    private UUID editedBy;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String snapshot;

    private LocalDateTime timestamp;

    // getters and setters
}