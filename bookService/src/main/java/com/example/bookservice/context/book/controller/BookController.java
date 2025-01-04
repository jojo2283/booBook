package com.example.bookservice.context.book.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.book.model.BookModel;
import com.example.bookservice.context.book.model.BookSearchRequest;
import com.example.bookservice.context.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.LIBRARY)
public class BookController {

    private final BookService bookService;

    @GetMapping("/find")
    public List<BookModel> findBooks(@ModelAttribute BookSearchRequest request) {
         return bookService.findBooks(request);
    }

}
