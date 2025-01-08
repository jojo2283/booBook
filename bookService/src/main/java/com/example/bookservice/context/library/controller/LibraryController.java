package com.example.bookservice.context.library.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.library.model.Library;
import com.example.bookservice.context.library.model.LibraryResponse;
import com.example.bookservice.context.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.LIBRARY)
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping("/allLibraries")
    public ResponseEntity<List<Library>> getAllLibraries() {
        return ResponseEntity.ok(libraryService.getLibrary());
    }

    @GetMapping("/allLibraries/{bookId}")
    public ResponseEntity<List<LibraryResponse>> findBookCopyInlabrary(@PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.findCopies(bookId));
    }
    @PostMapping("/new")
    public ResponseEntity<Library> createLibrary(@RequestBody Library library) {
        return ResponseEntity.ok(libraryService.create(library));
    }
}
