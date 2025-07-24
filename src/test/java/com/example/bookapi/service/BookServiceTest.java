package com.example.bookapi.service;

import com.example.bookapi.BookService.BookService;
import com.example.bookapi.exception.ResourceNotFoundException;
import com.example.bookapi.model.Book;
import com.example.bookapi.BookRepository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        // Create a common book object for tests
        book = new Book();
        book.setId(1L);
        book.setTitle("The Hobbit");
        book.setAuthor("J.R.R. Tolkien");
    }

    @Test
    void whenGetBookById_andBookExists_thenReturnBook() {
        // Arrange: Define the mock's behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act: Call the method to be tested
        Book foundBook = bookService.getBookById(1L);

        // Assert: Check the result
        assertNotNull(foundBook);
        assertEquals("The Hobbit", foundBook.getTitle());
        verify(bookRepository).findById(1L); // Verify that the mock was called
    }

    @Test
    void whenGetBookById_andBookDoesNotExist_thenThrowResourceNotFoundException() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(1L);
        });
        verify(bookRepository).findById(1L);
    }

    @Test
    void whenCreateBook_thenReturnSavedBook() {
        // Arrange
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Act
        Book savedBook = bookService.createBook(new Book());

        // Assert
        assertNotNull(savedBook);
        assertEquals(1L, savedBook.getId());
        verify(bookRepository).save(any(Book.class));
    }
}