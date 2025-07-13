package com.devtiro.quickStart.controllers;

import com.devtiro.quickStart.TestDataUtil;
import com.devtiro.quickStart.domain.dto.BookDTO;
import com.devtiro.quickStart.domain.entities.BookEntity;
import com.devtiro.quickStart.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;


    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHTTP201StatusCreated() throws Exception {
        BookDTO bookDTO = TestDataUtil.getBookDTOA(null);
        String createdBookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDTO.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdBookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsCreatedBook() throws Exception {
        BookDTO bookDTO = TestDataUtil.getBookDTOA(null);
        String createdBookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDTO.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Too Good To Be True")
        );
    }

    @Test
    public void testThatUpdateBookSuccessfullyReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.getBookA(null);
        BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDTO bookDTO = TestDataUtil.getBookDTOA(null);
        bookDTO.setIsbn(savedBookEntity.getIsbn());
        String createdBookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDTO.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Too Good To Be True")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAllBooksReturnsHTTP200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAllBooksReturnsBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.getBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("Too Good To Be True")
        );
    }

    @Test
    public void testThatGetBookReturnsHTTPStatus200WhenExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.getBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatSavedBookisReturned() throws Exception {
        BookEntity bookEntity = TestDataUtil.getBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Too Good To Be True")
        );
    }

    @Test
    public void deleteNonExistingBookReturns204NoContent() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + "123")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}

