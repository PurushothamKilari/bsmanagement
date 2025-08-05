package com.bookstore.bsmanagement.controller;

import com.bookstore.bsmanagement.dto.AuthRequest;
import com.bookstore.bsmanagement.dto.AuthResponse;
import com.bookstore.bsmanagement.entity.User;
import com.bookstore.bsmanagement.security.JwtUtil;
import com.bookstore.bsmanagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

   @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            
            return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername()));
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    /**
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // This is a placeholder for user registration
        // You would typically implement user creation logic here
        // userService.createUser(user);
        return ResponseEntity.ok(userService.createUser(user));
    }
}