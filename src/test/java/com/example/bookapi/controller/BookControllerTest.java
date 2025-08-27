package com.example.bookapi.controller;

// Import the missing BookController class
import com.example.bookapi.BookController.BookController;
import com.example.bookapi.model.Book;
import com.example.bookapi.BookService.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// Import the missing Autowired annotation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// The annotation must reference the class it is testing
@WebMvcTest(BookController.class)
class BookControllerTest {

    // Add @Autowired to inject the MockMvc instance
    @Autowired
    private MockMvc mockMvc;

    // This provides the mock service, fixing the Jenkins build failure
    @MockBean
    private BookService bookService;

    // Autowire ObjectMapper to convert objects to JSON
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

    // --- Your test methods will now work correctly ---
    @Test
    void whenCreateBook_thenReturnCreated() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void whenGetBookById_thenReturnBook() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book);

        mockMvc.perform(get("/api/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"));
    }

    @Test
    void whenUpdateBook_thenReturnOk() throws Exception {
        Book updatedBook = new Book();
        updatedBook.setTitle("The Hobbit Updated");

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Hobbit Updated"));
    }

    @Test
    void whenDeleteBook_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}