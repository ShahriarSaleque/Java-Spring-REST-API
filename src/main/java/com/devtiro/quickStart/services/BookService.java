package com.devtiro.quickStart.services;

import com.devtiro.quickStart.domain.entities.BookEntity;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity book);

}
