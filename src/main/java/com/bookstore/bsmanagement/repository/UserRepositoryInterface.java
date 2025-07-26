package com.bookstore.bsmanagement.repository;
import com.bookstore.bsmanagement.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface defining the contract for user repository operations.
 * Provides CRUD operations for user management.
 * 
 * @author BookStore Management System
 * @version 2.0
 */
public interface UserRepositoryInterface extends JpaRepository<User,Long> {

    User findByUsername(String username);

   
   
}
