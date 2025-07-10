package com.devtiro.quickStart.services.impl;

import com.devtiro.quickStart.domain.entities.BookEntity;
import com.devtiro.quickStart.repositories.BookRepository;
import com.devtiro.quickStart.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }
}
