package com.example.bookservice.context.book.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.book.model.BookCopyModel;
import com.example.bookservice.context.book.service.CopiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.COPIES)
public class CopiesController {
    private final CopiesService copiesService;

    @GetMapping
    public ResponseEntity<List<BookCopyModel>> findBooks(@RequestParam(required = false) Long bookId,@RequestParam(required = false) Long libraryId) {
        return ResponseEntity.ok(copiesService.findBooks(bookId,libraryId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCopies(@PathVariable Long id) {
        if (copiesService.deleteBook(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
