package com.example.bookservice.context.library.service;

import com.example.bookservice.context.book.model.BookCopy;
import com.example.bookservice.context.book.repository.BookRepository;
import com.example.bookservice.context.book.repository.CopiesRepository;
import com.example.bookservice.context.library.model.LibraryResponse;
import com.example.bookservice.context.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final CopiesRepository copiesRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;


    public List<LibraryResponse> findCopies(Long bookId) {
        List<BookCopy> copies=copiesRepository.findByBookId(bookId);
        return copies.stream().map(LibraryResponse::fromBookCopyToResponse).collect(Collectors.toList());
    }
}
