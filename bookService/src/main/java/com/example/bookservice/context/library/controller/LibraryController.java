package com.example.bookservice.context.library.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.library.model.LibraryResponse;
import com.example.bookservice.context.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.LIBRARY)
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/allLibraries/{bookId}")
    public ResponseEntity<List<LibraryResponse>> findBookCopyInlabrary(@PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.findCopies(bookId));
    }
}
