package com.juanf.bookservice;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.juanf.bookservice.entity.Book;
import com.juanf.bookservice.entity.Genre;
import com.juanf.bookservice.repository.BookRepository;

@DataJpaTest
public class BookRepositoryMockTest {

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void whenFindByGenre_thenReturnListBook() {
		Book book01= Book.builder()
				.name("Papelucho")
				.genre(Genre.builder().id(1L).build())
				.author("")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("1240"))
				.status("Created")
				.createAt(new Date()).build();
		bookRepository.save(book01);

		List<Book> founds= bookRepository.findByGenre(book01.getGenre());

		Assertions.assertThat(founds.size()).isEqualTo(3);

	}
}
