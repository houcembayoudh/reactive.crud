package com.training.reactive.crud;

import com.training.reactive.crud.dao.BookRepository;
import com.training.reactive.crud.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	@Bean
	CommandLineRunner start( BookRepository bookRepository){
		final var book = new Book("Omar", "Sportage");
		bookRepository.addBook(book);

	}*/

}
