package com.ssafy.polaris.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.polaris.book.domain.Book;

public interface BookRepository extends JpaRepository<Book, String> {
}
