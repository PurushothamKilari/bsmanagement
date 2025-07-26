package com.bookstore.bsmanagement.service;
import java.util.List;

import com.bookstore.bsmanagement.entity.Book;


public interface BookService {
    Boolean addBook(Book book);
    List<Book> getAllBooks();
    Book findBookById(String id);
    boolean removeBook(String id);  
    Book updateBook(String id, Book updatedBook);
    void deleteBook(Long id);


    // Uncomment the following lines if you want to use an in-memory storage for books
    // This is not recommended for production use, but can be useful for testing or prototyping

    // private final Map<String, Book> books = new ConcurrentHashMap<>();

    // public void addBook(Book book) {
    //     books.put(book.getId(), book);
    // }

    // public List<Book> getAllBooks() {
    //     return java.util.Collections.unmodifiableList(new ArrayList<>(books.values()));
    // }

    // public Book findBookById(String id) {
    //     return books.get(id);
    // }

    // public boolean removeBook(String id) {
    //     return books.remove(id) != null;
    // }

    // public void updateBook(String id, Book updatedBook) {
    //     books.put(id, updatedBook);
    // }
}

