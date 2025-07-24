package com.bookstore.bsmanagement.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;



    @Entity
    @Table(name = "orders")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String customerName;

        @Column(nullable = false)
        private LocalDateTime orderDate;

        @Column(nullable = false)
        private Double totalAmount;

        // Add relationships and other fields as needed
    
}
