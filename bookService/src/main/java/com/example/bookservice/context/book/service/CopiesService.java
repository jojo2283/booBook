package com.example.bookservice.context.book.service;

import com.example.bookservice.context.book.exception.BookAlreadyExistException;
import com.example.bookservice.context.book.exception.BookNotFoundException;
import com.example.bookservice.context.book.model.BookCopy;
import com.example.bookservice.context.book.model.BookCopyModel;
import com.example.bookservice.context.book.repository.BookRepository;
import com.example.bookservice.context.book.repository.CopiesRepository;
import com.example.bookservice.context.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CopiesService {
    private final CopiesRepository copiesRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

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

    public BookCopyModel createBook(BookCopyModel book) {
        BookCopy bookFromIN = copiesRepository.findByInventoryNumber(book.getInventoryNumber());
        if (bookFromIN != null) {
            throw new BookAlreadyExistException("Book copy already exist");
        }
        BookCopy newBook = new BookCopy();


        newBook.setAvailable(book.getAvailable());
        newBook.setBook(bookRepository.findById(book.getBookId()).orElseThrow());
        newBook.setLibrary(libraryRepository.findById(book.getLibraryId()).orElseThrow());
        newBook.setInventoryNumber(book.getInventoryNumber());
        return BookCopyModel.toModel(copiesRepository.save(newBook));


    }

    public BookCopyModel updateCopy(Long id, BookCopyModel bookCopy) {
        BookCopy oldBook = copiesRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        oldBook.setAvailable(bookCopy.getAvailable());
        oldBook.setBook(bookRepository.findById(bookCopy.getBookId()).orElseThrow());
        oldBook.setLibrary(libraryRepository.findById(bookCopy.getLibraryId()).orElseThrow());
        oldBook.setInventoryNumber(bookCopy.getInventoryNumber());
        return BookCopyModel.toModel(copiesRepository.save(oldBook));

    }
}
