package com.example.bookservice.context.book.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.book.model.Book;
import com.example.bookservice.context.book.model.BookModel;
import com.example.bookservice.context.book.model.BookSearchRequest;
import com.example.bookservice.context.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.LIBRARY)
public class BookController {

    private final BookService bookService;

    @GetMapping("/find")
    public ResponseEntity<List<BookModel>> findBooks(@ModelAttribute BookSearchRequest request) {
        return ResponseEntity.ok(bookService.findBooks(request));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @PostMapping("/newBook")
    public ResponseEntity<BookModel> createBook(@RequestBody Book Book) {
        return ResponseEntity.ok(bookService.createBook(Book));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable Long id, @RequestBody Book Book) {
        return ResponseEntity.ok(bookService.updateBook(id, Book));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteHumanBeing(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
