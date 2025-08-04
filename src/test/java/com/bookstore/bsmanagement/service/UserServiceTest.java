package com.bookstore.bsmanagement.service;

import com.bookstore.bsmanagement.entity.User;
import com.bookstore.bsmanagement.repository.UserRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryInterface userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .fullName("Test User")
                .password("password123")
                .build();
    }

    @Test
    void findUserByUsername_UserExists_ReturnsUser() {
        // Arrange
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenReturn(testUser);

        // Act
        User result = userService.findUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void findUserByUsername_UserNotFound_ThrowsException() {
        // Arrange
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findUserByUsername(username);
        });
        assertEquals("User not found with username: nonexistent", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void findAll_ReturnsAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(
                testUser,
                User.builder().id(2L).username("user2").email("user2@example.com").build()
        );
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAllUsers_ReturnsAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(
                testUser,
                User.builder().id(2L).username("user2").email("user2@example.com").build()
        );
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_UserExists_ReturnsUser() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_UserNotFound_ReturnsNull() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void createUser_ReturnsCreatedUser() {
        // Arrange
        User newUser = User.builder()
                .username("newuser")
                .email("new@example.com")
                .fullName("New User")
                .password("newpass123")
                .build();
        
        User savedUser = User.builder()
                .id(3L)
                .username("newuser")
                .email("new@example.com")
                .fullName("New User")
                .password(passwordEncoder.encode("newpass123"))
                .build();
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.createUser(newUser);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("newuser", result.getUsername());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void updateUser_UserExists_ReturnsUpdatedUser() {
        // Arrange
        Long userId = 1L;
        User updatedDetails = User.builder()
                .username("updateduser")
                .email("updated@example.com")
                .fullName("Updated User")
                .password(passwordEncoder.encode("updatedpass"))
                .build();
        
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(updatedDetails);

        // Act
        User result = userService.updateUser(userId, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("updateduser", result.getUsername());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).save(updatedDetails);
    }

    @Test
    void updateUser_UserNotFound_ReturnsNull() {
        // Arrange
        Long userId = 999L;
        User updatedDetails = User.builder()
                .username("updateduser")
                .email("updated@example.com")
                .build();
        
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act
        User result = userService.updateUser(userId, updatedDetails);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_UserExists_ReturnsTrue() {
        // Arrange
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        // Act
        boolean result = userService.deleteUser(userId);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUser_UserNotFound_ReturnsFalse() {
        // Arrange
        Long userId = 999L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act
        boolean result = userService.deleteUser(userId);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void getUsersByPage_ReturnsPaginatedUsers() {
        // Arrange
        int page = 0;
        int size = 10;
        List<User> users = Arrays.asList(
                testUser,
                User.builder().id(2L).username("user2").email("user2@example.com").build()
        );
        
        when(userRepository.findAll(any(PageRequest.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl<>(users));

        // Act
        List<User> result = userService.getUsersByPage(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void FetchUserDataById_ThrowsUnsupportedOperationException() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            userService.FetchUserDataById(1L);
        });
    }
}