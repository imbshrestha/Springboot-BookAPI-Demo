package com.example.bookapi.BookService;

import com.example.bookapi.model.Book;
import com.example.bookapi.BookRepository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bookapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService {

    // Get the logger instance from SLF4J's LoggerFactory
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        logger.debug("Attempting to fetch book with id: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book not found with id: {}", id);
                    return new ResourceNotFoundException("Book not found with id: " + id);
                });
    }

    public Book createBook(Book book) {
        logger.info("Creating new book with title: '{}'", book.getTitle());
        Book savedBook = bookRepository.save(book);
        logger.info("Successfully created book with id: {}", savedBook.getId());
        return savedBook;
    }

    public Book updateBook(Long id, Book bookDetails) {
        logger.info("Attempting to update book with id: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        Book updatedBook = bookRepository.save(book);
        logger.info("Successfully updated book with id: {}", id);
        return updatedBook;
    }

    public void deleteBook(Long id) {
        logger.info("Attempting to delete book with id: {}", id);
        if (!bookRepository.existsById(id)) {
            logger.error("Failed to delete. Book not found with id: {}", id);
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }

        bookRepository.deleteById(id);
        logger.info("Successfully deleted book with id: {}", id);
    }
}