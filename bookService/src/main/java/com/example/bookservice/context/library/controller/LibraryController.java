package com.example.bookservice.context.library.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.book.service.BookCopyCsvService;
import com.example.bookservice.context.library.model.Library;
import com.example.bookservice.context.library.model.LibraryResponse;
import com.example.bookservice.context.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.LIBRARY)
public class LibraryController {
    private final LibraryService libraryService;
    private final BookCopyCsvService bookCopyCsvService;

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
    @PostMapping("/upload")
    public ResponseEntity<String> importBookCopies(@RequestParam("file") MultipartFile file) {
        try {
            bookCopyCsvService.importBookCopiesFromCsv(file.getInputStream());
            return ResponseEntity.ok("Book copies imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error importing book copies: " + e.getMessage());
        }
    }
}
