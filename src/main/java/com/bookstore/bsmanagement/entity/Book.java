package com.bookstore.bsmanagement.entity;


import jakarta.persistence.*;
import lombok.*;



@Data // Lombok: generates getters, setters, toString, equals, and hashCode
@Entity // Hibernate: marks this class as a JPA entity
@Table(name = "books") // Hibernate: maps to 'books' table
@NoArgsConstructor // Lombok: generates no-args constructor
@AllArgsConstructor // Lombok: generates all-args constructor
@Builder // Lombok: enables builder pattern
public class Book {

    @Id
    @Column(length = 8, unique = true, nullable = false)
    private String id; // 8-digit unique code as primary key

    @PrePersist
    private void ensureId() {
        if (this.id == null) {
            this.id = generateUniqueId();
        }
    }

    private String generateUniqueId() {
        // Use current time and random for better uniqueness
        long millis = System.currentTimeMillis() % 100_000_000L;
        int random = (int)(Math.random() * 100_000_000);
        String code = String.format("%08d", (millis + random) % 100_000_000);
        return code;
    }

    @Column(nullable = false)
    private String title; // Book title

    @Column(nullable = false)
    private String author; // Book author(s)

    @Column(nullable = false)
    private String genre; // Book genre

    @Column(unique = true, nullable = false, length = 13)
    private String isbn; // ISBN (unique, 13 chars)

    @Column(nullable = false)
    private Double price; // Book price

    @Column(length = 2000)
    private String description; // Book description

    @Column(nullable = false)
    private Integer stockQuantity; // Stock quantity

    @Column(length = 500)
    private String imageUrl; // Cover image URL
}
