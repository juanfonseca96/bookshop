package com.juanf.bookservice;

import java.util.Date;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.juanf.bookservice.entity.Book;
import com.juanf.bookservice.entity.Genre;
import com.juanf.bookservice.repository.BookRepository;
import com.juanf.bookservice.service.BookService;
import com.juanf.bookservice.service.BookServiceImpl;

@SpringBootTest
public class BookServiceMockTest {

	@Mock
	private BookRepository bookRepository;
	
	private BookService bookService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		bookService = new BookServiceImpl(bookRepository);
		Book book= Book.builder()
				.id(1L)
				.name("Papelucho 2")
				.genre(Genre.builder().id(1L).build())
				.author("")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("900"))
				.status("Created")
				.createAt(new Date()).build();
				
		Mockito.when(bookRepository.findById(1L))
			.thenReturn(Optional.of(book));
		Mockito.when(bookRepository.save(book)).thenReturn(book);
		
	}
	@Test
	public void WhenValidGetID_ThenReturnBook() {
		
		Book found = bookService.getBook(1L);
		Assertions.assertThat(found.getName()).isEqualTo("Papelucho 2");
	}
	@Test
	public void whenValidUpdateStock_ThenRunNewStock() {
		Book newStock = bookService.updateStock(1L, Double.parseDouble("8"));
		Assertions.assertThat(newStock.getStock()).isEqualTo(18);
		
	}
	
	
}
