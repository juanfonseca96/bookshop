package com.juanf.bookservice.service;

import java.util.List;

import com.juanf.bookservice.entity.Book;
import com.juanf.bookservice.entity.Genre;

public interface BookService {
	
	public List<Book>listAllBook();
	
	public Book getBook(Long id);
	
	public Book createBook(Book book);
	
	public Book updateBook(Book book);
	
	public Book deleteBook(Long id);
	
	public List<Book>findByGenre(Genre genre);
	
	public Book updateStock(Long id, double quantity);
	
	
}
