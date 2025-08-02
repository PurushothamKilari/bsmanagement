package com.bookstore.bsmanagement.service;

import com.bookstore.bsmanagement.entity.Book;
import com.bookstore.bsmanagement.repository.BookRepositoryInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepositoryInterface bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = Book.builder()
                .id("12345678")
                .title("Test Book")
                .author("Test Author")
                .genre("Fiction")
                .isbn("9781234567890")
                .price(29.99)
                .description("Test book description")
                .stockQuantity(10)
                .imageUrl("http://example.com/image.jpg")
                .build();
    }

    @Test
    void addBook_ValidBook_ReturnsTrue() {
        // Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        // Act
        Boolean result = bookService.addBook(testBook);

        // Assert
        assertTrue(result);
        verify(bookRepository, times(1)).save(testBook);
    }

    @Test
    void addBook_NullBook_ReturnsFalse() {
        // Arrange
        when(bookRepository.save(null)).thenReturn(null);

        // Act
        Boolean result = bookService.addBook(null);

        // Assert
        assertFalse(result);
        verify(bookRepository, times(1)).save(null);
    }

    @Test
    void getAllBooks_ReturnsAllBooks() {
        // Arrange
        List<Book> books = Arrays.asList(
                testBook,
                Book.builder()
                        .id("87654321")
                        .title("Another Book")
                        .author("Another Author")
                        .genre("Non-Fiction")
                        .isbn("9780987654321")
                        .price(19.99)
                        .stockQuantity(5)
                        .build()
        );
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findBookById_BookExists_ReturnsBook() {
        // Arrange
        String bookId = "12345678";
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(testBook));

        // Act
        Book result = bookService.findBookById(bookId);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Test Book", result.getTitle());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void findBookById_BookNotFound_ReturnsNull() {
        // Arrange
        String bookId = "99999999";
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        Book result = bookService.findBookById(bookId);

        // Assert
        assertNull(result);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void findBookById_NullId_ReturnsNull() {
        // Arrange
        when(bookRepository.findById(null)).thenReturn(Optional.empty());

        // Act
        Book result = bookService.findBookById(null);

        // Assert
        assertNull(result);
        verify(bookRepository, times(1)).findById(null);
    }

    @Test
    void removeBook_BookExists_ReturnsTrue() {
        // Arrange
        String bookId = "12345678";
        when(bookRepository.existsById(bookId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(bookId);

        // Act
        boolean result = bookService.removeBook(bookId);

        // Assert
        assertTrue(result);
        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void removeBook_BookNotFound_ReturnsFalse() {
        // Arrange
        String bookId = "99999999";
        when(bookRepository.existsById(bookId)).thenReturn(false);

        // Act
        boolean result = bookService.removeBook(bookId);

        // Assert
        assertFalse(result);
        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void removeBook_NullId_ReturnsFalse() {
        // Arrange
        when(bookRepository.existsById(null)).thenReturn(false);

        // Act
        boolean result = bookService.removeBook(null);

        // Assert
        assertFalse(result);
        verify(bookRepository, times(1)).existsById(null);
        verify(bookRepository, never()).deleteById(any());
    }

    @Test
    void updateBook_BookExists_ReturnsUpdatedBook() {
        // Arrange
        String bookId = "12345678";
        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Updated Book")
                .author("Updated Author")
                .genre("Updated Genre")
                .isbn("9781234567890")
                .price(39.99)
                .description("Updated description")
                .stockQuantity(15)
                .imageUrl("http://example.com/updated.jpg")
                .build();
        
        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Act
        Book result = bookService.updateBook(bookId, updatedBook);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("Updated Book", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    void updateBook_BookNotFound_ReturnsBookWithoutSaving() {
        // Arrange
        String bookId = "99999999";
        Book updatedBook = Book.builder()
                .id(bookId)
                .title("Updated Book")
                .build();
        
        when(bookRepository.existsById(bookId)).thenReturn(false);

        // Act
        Book result = bookService.updateBook(bookId, updatedBook);

        // Assert
        assertNotNull(result);
        assertEquals(bookId, result.getId());
        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void updateBook_NullId_ReturnsBookWithoutSaving() {
        // Arrange
        Book updatedBook = Book.builder()
                .title("Updated Book")
                .build();
        
        when(bookRepository.existsById(null)).thenReturn(false);

        // Act
        Book result = bookService.updateBook(null, updatedBook);

        // Assert
        assertNotNull(result);
        verify(bookRepository, times(1)).existsById(null);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_ThrowsUnsupportedOperationException() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> {
            bookService.deleteBook(1L);
        });
    }
}
