package com.micro.content;

import com.micro.user.entity.User;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private ContentStatus status;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private int version;

    // Getters & Setters
}