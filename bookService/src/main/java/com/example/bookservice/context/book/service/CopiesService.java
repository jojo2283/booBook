package com.example.bookservice.context.book.service;

import com.example.bookservice.context.book.model.BookCopy;
import com.example.bookservice.context.book.model.BookCopyModel;
import com.example.bookservice.context.book.repository.CopiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CopiesService {
    private final CopiesRepository copiesRepository;

    public List<BookCopyModel> findBooks(Long bookId, Long libraryId) {
        List<BookCopy> copiesList;
        if (bookId != null && libraryId != null) {
            copiesList = copiesRepository.findByBookIdAndLibraryId(bookId, libraryId);

        } else if (bookId != null) {
            copiesList = copiesRepository.findByBookId(bookId);

        } else if (libraryId != null) {
            copiesList = copiesRepository.findByLibraryId(libraryId);

        } else {
            copiesList = copiesRepository.findAll();
        }

        return copiesList != null ? copiesList.stream().map(BookCopyModel::toModel).collect(Collectors.toList()) : null;
    }

    public boolean deleteBook(Long id) {
        BookCopy book = copiesRepository.findById(id).orElse(null);

        if (book == null) {
            return false;
        }

        copiesRepository.deleteById(id);

        return true;
    }
}
