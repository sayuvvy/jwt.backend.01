package com.booksample.site.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bsn_token")
public class Token {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    private boolean expired;
    private boolean revoked;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;
    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;
}
