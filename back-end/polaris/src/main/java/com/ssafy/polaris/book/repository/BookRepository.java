package com.ssafy.polaris.book.repository;

import com.ssafy.polaris.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
