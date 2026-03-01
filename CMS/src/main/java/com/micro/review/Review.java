package com.micro.review;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID contentId;

    private UUID reviewerId;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private String decision; // APPROVED or REJECTED

    private LocalDateTime timestamp;

    // getters and setters
}