package com.bookstore.bsmanagement.config;

import com.bookstore.bsmanagement.entity.User;
import com.bookstore.bsmanagement.repository.UserRepositoryInterface;

import java.util.HashSet;
import java.util.Set;

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
        admin.setRole(Set.of("ROLE_ADMIN"));
        admin.setActive(true);
        userRepository.save(admin);

        // Create regular user
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setEmail("user@bookstore.com");
         Set<String> userRoles = new HashSet<>();
        userRoles.add("ADMIN");
        userRoles.add("USER");
        user.setRole(userRoles);
        user.setActive(true);
        userRepository.save(user);

        // Create manager user
        User manager = new User();
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("manager123"));
        manager.setEmail("manager@bookstore.com");
        Set<String> managerRoles = new HashSet<>();
        managerRoles.add("ADMIN");
        managerRoles.add("MANAGER");
        manager.setRole(managerRoles);
        manager.setActive(true);
        userRepository.save(manager);

        System.out.println("Sample users created successfully!");
        System.out.println("Admin: admin/admin123");
        System.out.println("User: user/user123");
        System.out.println("Manager: manager/manager123");
    }
}