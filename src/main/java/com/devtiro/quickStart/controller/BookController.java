package com.devtiro.quickStart.controller;

import com.devtiro.quickStart.domain.dto.BookDTO;
import com.devtiro.quickStart.domain.entities.BookEntity;
import com.devtiro.quickStart.mappers.Mapper;
import com.devtiro.quickStart.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDTO> bookMapper;

    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDTO> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDTO> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDTO);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDTO savedBookDTO = bookMapper.mapTo(savedBookEntity);

        if(!bookExists) {
            return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(savedBookDTO, HttpStatus.OK);
        }
    }

    @PatchMapping("/books/{isbn}")
    public ResponseEntity<BookDTO> partialUpdateBook(@PathVariable("isbn") String isbn,
                                                     @RequestBody BookDTO bookDTO) {

        boolean bookExists = bookService.isExists(isbn);

        if(!bookExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            BookEntity bookEntity = bookMapper.mapFrom(bookDTO);
            BookEntity savedBookEntity = bookService.partialUpdate(isbn, bookEntity);

            return new ResponseEntity<>(
                    bookMapper.mapTo(savedBookEntity),
                    HttpStatus.OK
            );
        }
    }


    @GetMapping("/books")
    public Page<BookDTO> listBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.findAll(pageable);

        return books.map(bookMapper::mapTo);
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> bookEntity = bookService.findOne(isbn);

        return bookEntity.map(bookEntity1 -> {
            BookDTO bookDTO = bookMapper.mapTo(bookEntity1);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
