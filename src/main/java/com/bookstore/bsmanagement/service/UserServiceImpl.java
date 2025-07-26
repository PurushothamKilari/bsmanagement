package com.bookstore.bsmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bookstore.bsmanagement.entity.User;

import com.bookstore.bsmanagement.repository.UserRepositoryInterface;

@Service
public class UserServiceImpl implements UserService {

   @Autowired
   private UserRepositoryInterface userRepository;

   @Override
   public User findUserByUsername(String username){
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
     }

     @Override
     public User FetchUserDataById(Long id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'FetchUserDataById'");
     }

     @Override
     public List<User> findAll() {
         return userRepository.findAll();
     }

     @Override
     public List<User> getAllUsers() {
         return userRepository.findAll();
     }

     @Override
     public User getUserById(Long id) {
         return userRepository.findById(id).orElse(null);
     }

     @Override
     public User createUser(User user) {
         return userRepository.save(user);
     }

     @Override
     public User updateUser(Long id, User userDetails) {
         if (userRepository.existsById(id)) {
             userDetails.setId(id);
             return userRepository.save(userDetails);
         }
         return null; // or throw an exception if preferred
     }

     @Override
     public boolean deleteUser(Long id) {
         if (userRepository.existsById(id)) {
               userRepository.deleteById(id);
               return true;
         }
         return false;
     }

     @Override
     public List<User> getUsersByPage(int page, int size) {
         // Assuming you have a method in the repository to handle pagination
         // PageRequest pageable = PageRequest.of(page, size);
         // return userRepository.findAll(pageable).getContent();
         return userRepository.findAll(PageRequest.of(page, size)).getContent();
     }
}
      // TODO Auto-generated method stub
