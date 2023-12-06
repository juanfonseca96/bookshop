package com.juanf.bookservice.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanf.bookservice.entity.Book;
import com.juanf.bookservice.entity.Genre;
import com.juanf.bookservice.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

	//@Autowired
	private final BookRepository bookRepository;
	
	@Override
	public List<Book> listAllBook() {
		return bookRepository.findAll();
	}

	@Override
	public Book getBook(Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	@Override
	public Book createBook(Book book) {
		book.setStatus("CREATED");
		book.setCreateAt(new Date());
		
		return bookRepository.save(book);
	}

	@Override
	public Book updateBook(Book book) {
		Book bookDB = getBook(book.getId());
		if(null==bookDB) {
			return null;
		}
		bookDB.setName(book.getName());
		bookDB.setAuthor(book.getAuthor());
		bookDB.setGenre(book.getGenre());
		bookDB.setPrice(book.getPrice());
		bookDB.setStatus(book.getStatus());
		
		return bookRepository.save(bookDB);
	}

	@Override
	public Book deleteBook(Long id) {
		Book bookDB = getBook(id);
		if(null==bookDB) {
			return null;
		}
		bookDB.setStatus("DELETED");
		return bookRepository.save(bookDB);
	}

	@Override
	public List<Book> findByGenre(Genre genre) {
		return bookRepository.findByGenre(genre);
	}

	@Override
	public Book updateStock(Long id, double quantity) {
		Book bookDB = getBook(id);
		if(null==bookDB) {
			return null;
		}
		Double stock = bookDB.getStock() + quantity;
		bookDB.setStock(stock);
		return bookRepository.save(bookDB);
	}

}
