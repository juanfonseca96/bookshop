package com.juanf.bookservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanf.bookservice.entity.Book;
import com.juanf.bookservice.service.BookService;

@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> listBook(){
		List<Book> books = bookService.listAllBook();
		if(books.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(books);
	}
	//obtener libro en especifico
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
		Book book = bookService.getBook(id);
		if(null==book) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(book);
	}
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book, BindingResult result){
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
		}
		Book bookCreate = bookService.createBook(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(bookCreate);
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book){
		book.setId(id);
		Book bookDB = bookService.updateBook(book);
		if(bookDB==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bookDB);
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id){
		Book bookDelete = bookService.deleteBook(id);
		if(bookDelete==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bookDelete);
		
	}
	@GetMapping("/books/{id}/stock")
	public ResponseEntity<Book> updateStockBook(@PathVariable Long id, @RequestParam(name = "quantity", required= true)Double quantity){
		Book book= bookService.updateStock(id, quantity);
		if(book==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(book);
		
	}
	
	private String formatMessage(BindingResult result) {
		List<Map<String, String>> errors = result.getFieldErrors().stream()
				.map(err ->{
					Map<String, String> error = new HashMap<>();
					error.put(err.getField(), err.getDefaultMessage());
					return error;
				}).collect(Collectors.toList());
		ErrorMessage errorMessage = ErrorMessage.builder()
				.code("01")
				.messages(errors).build();
		ObjectMapper mapper = new ObjectMapper();
		String jsonString="";
		try {
			jsonString = mapper.writeValueAsString(errorMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return jsonString;
		
	}
}
