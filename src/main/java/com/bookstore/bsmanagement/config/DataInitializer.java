package com.bookstore.bsmanagement.config;

import com.bookstore.bsmanagement.entity.User;
import com.bookstore.bsmanagement.repository.UserRepositoryInterface;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepositoryInterface userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create sample users only if no users exist
        if (userRepository.count() == 0) {
            createSampleUsers();
        }
    }

    private void createSampleUsers() {
        // Create admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setEmail("admin@bookstore.com");
        admin.setRole("ROLE_ADMIN");
        admin.setActive(true);
        userRepository.save(admin);

        // Create regular user
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setEmail("user@bookstore.com");
        user.setRole("ROLE_USER");
        user.setActive(true);
        userRepository.save(user);

        // Create manager user
        User manager = new User();
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("manager123"));
        manager.setEmail("manager@bookstore.com");
        manager.setRole("ROLE_MANAGER");
        manager.setActive(true);
        userRepository.save(manager);

        System.out.println("Sample users created successfully!");
        System.out.println("Admin: admin/admin123");
        System.out.println("User: user/user123");
        System.out.println("Manager: manager/manager123");
    }
}