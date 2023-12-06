package com.juanf.bookservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanf.bookservice.entity.Book;
import com.juanf.bookservice.entity.Genre;

public interface BookRepository extends JpaRepository<Book, Long> {

	public List<Book> findByGenre(Genre genre);
}
