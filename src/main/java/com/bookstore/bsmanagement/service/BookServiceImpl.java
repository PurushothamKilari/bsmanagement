package com.bookstore.bsmanagement.service;

import com.bookstore.bsmanagement.entity.Book;
import com.bookstore.bsmanagement.repository.BookRepositoryInterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service

public class BookServiceImpl  implements BookService {

//private final BookRepositoryInterface bookRepository;


    @Autowired
    private BookRepositoryInterface bookRepository;

   
    @Override
    public Boolean addBook(Book book) {
        return bookRepository.save(book) != null;
    }
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    @Override
    public Book findBookById(String id) {
        return bookRepository.findById(id).orElse(null);
    }
    @Override
    public boolean removeBook(String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public Book updateBook(String id, Book updatedBook) {
        if (bookRepository.existsById(id)) {
            updatedBook.setId(id);
            bookRepository.save(updatedBook);
        }
        return updatedBook;
    }
    @Override
    public void deleteBook(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }

}
