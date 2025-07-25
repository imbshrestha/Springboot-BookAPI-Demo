package com.example.bookapi.controller;

import com.example.bookapi.BookController.BookController;
import com.example.bookapi.model.Book;
import com.example.bookapi.BookService.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// The @WebMvcTest annotation still targets our controller
@WebMvcTest(BookController.class)
// We explicitly tell the test which configuration to use.
@ContextConfiguration(classes = BookControllerTest.TestConfig.class)
class BookControllerTest {

    // This static nested configuration class defines our mock beans.
    // It's a modern replacement for using @MockBean.
    @Configuration
    static class TestConfig {
        @Bean
        public BookService bookService() {
            // Manually create and return the mock object.
            return Mockito.mock(BookService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    // The mock service is now autowired from our TestConfig
    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("The Hobbit");
        book.setAuthor("J.R.R. Tolkien");
        book.setIsbn("12345");
    }

    @Test
    void whenCreateBook_thenReturnCreated() throws Exception {
        // Arrange
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Hobbit"));
    }

    @Test
    void whenGetBookById_thenReturnBook() throws Exception {
        // Arrange
        when(bookService.getBookById(1L)).thenReturn(book);

        // Act & Assert
        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"));
    }

    @Test
    void whenUpdateBook_thenReturnOk() throws Exception {
        // Arrange
        Book updatedBook = new Book();
        updatedBook.setTitle("The Hobbit Updated");

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

        // Act & Assert
        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Hobbit Updated"));
    }

    @Test
    void whenDeleteBook_thenReturnNoContent() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}