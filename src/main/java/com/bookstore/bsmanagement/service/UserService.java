package com.bookstore.bsmanagement.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookstore.bsmanagement.entity.User;


@Service
public interface UserService {

     User FetchUserDataById(Long id);
      /**
     * Finds a user by their username.
     * 
     * @param username the username of the user to find
     * @return the User object if found, null otherwise
     */
    User findUserByUsername(String username);

    /**
     * Finds all users in the repository.
     * 
     * @return a list of all User objects
     */
    List<User> findAll();
List<User> getAllUsers();

/**
 * Returns a paginated list of users.
 * 
 * @param page the page number (0-based)
 * @param size the size of the page
 * @return a list of users for the requested page
 */
List<User> getUsersByPage(int page, int size);
User getUserById(Long id);
User createUser(User user);
User updateUser(Long id, User userDetails);
boolean deleteUser(Long id);
        
  
   
}
