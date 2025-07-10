package com.devtiro.quickStart;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Log
public class BooksAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksAPIApplication.class, args);
	}
}
