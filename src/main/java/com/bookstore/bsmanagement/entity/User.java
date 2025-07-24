package com.bookstore.bsmanagement.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.Set;



    @Entity
    @Table(name = "users")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 50)
        private String username;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false, unique = true, length = 100)
        private String email;

        @Column(length = 100)
        private String fullName;

        @Column(length = 50)
        private String createdBy;

        @Column(length = 50)
        private String updatedBy;

        @CreationTimestamp
        @Column(updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        @Column
        private LocalDateTime lastModified;

        @Column(length = 255)
        private String auditDetails;

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
        @Column(name = "role")
        private Set<String> roles;

        @ElementCollection(fetch = FetchType.EAGER)
        @CollectionTable(name = "user_access", joinColumns = @JoinColumn(name = "user_id"))
        @Column(name = "access")
        private Set<String> access;
}
