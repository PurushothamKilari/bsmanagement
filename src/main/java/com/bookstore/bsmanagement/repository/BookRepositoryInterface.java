package com.bookstore.bsmanagement.repository;

import com.bookstore.bsmanagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoryInterface extends JpaRepository<Book, String> {

}
