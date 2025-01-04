package com.example.bookservice.context.book.service;

import com.example.bookservice.context.book.exception.BookAlreadyExistException;
import com.example.bookservice.context.book.exception.BookNotFoundException;
import com.example.bookservice.context.book.model.Book;
import com.example.bookservice.context.book.model.BookModel;
import com.example.bookservice.context.book.model.BookSearchRequest;
import com.example.bookservice.context.book.repository.BookRepository;
import com.example.bookservice.context.book.specification.BookSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookModel> findBooks(BookSearchRequest request) {
        Specification<Book> spec = Specification.where(null); // Начинаем с пустой спецификации

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            spec = spec.and(BookSpecifications.hasTitleLike(request.getName()));
        }
        if (request.getAuthors() != null) {
            spec = spec.and(BookSpecifications.hasAuthors(request.getAuthors()));
        }

        if (request.getMinCopies() != null) {
            spec = spec.and(BookSpecifications.hasMinCopies(request.getMinCopies()));
        }

        if (request.getMaxCopies() != null) {
            spec = spec.and(BookSpecifications.hasMaxCopies(request.getMaxCopies()));
        }

        if (request.getGenres() != null) {
            spec = spec.and(BookSpecifications.hasGenre(request.getGenres()));
        }


        if (request.getThemes() != null) {
            spec = spec.and(BookSpecifications.hasTheme(request.getThemes()));
        }

        // Фильтрация по издателю
        if (request.getPublishers() != null) {
            spec = spec.and(BookSpecifications.hasPublisher(request.getPublishers()));
        }

        // Фильтрация по статусу
        if (request.getAvailable() != null && request.getAvailable()) {
            spec = spec.and(BookSpecifications.hasAvailableCopies());
        } else if (request.getAvailable() != null) {
            spec = spec.and(BookSpecifications.hasNoAvailableCopies());
        }
//
//        // Фильтрация по популярности
//        if (request.getPopularity() != null) {
//            spec = spec.and(BookSpecifications.hasPopularity(request.getPopularity()));
//        }
//
//        // Фильтрация по рейтингу
//        if (request.getRating() != null) {
//            spec = spec.and(BookSpecifications.hasRating(request.getRating()));
//        }
//
//        // Сортировка по полю
//        if (request.getSortField() != null && !request.getSortField().trim().isEmpty()) {
//            spec = spec.and(BookSpecifications.sortByField(request.getSortField(), request.isSortAscending()));
//        }


        return bookRepository.findAll(spec).stream()
                .map(BookModel::toModel).collect(Collectors.toList());
    }

    public BookModel findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));


        return ResponseEntity.ok(BookModel.toModel(book)).getBody();

    }

    public BookModel createBook(Book book) {
        Book bookFromISBN = bookRepository.findBookByISBN(book.getISBN());
        if (bookFromISBN != null) {
            throw new BookAlreadyExistException("Book already exist");
        }

        return BookModel.toModel(bookRepository.save(book));
    }

    public BookModel updateBook(Long id, Book book) {
        Book oldBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        oldBook.setAuthors(book.getAuthors());
        oldBook.setISBN(book.getISBN());
        oldBook.setTitle(book.getTitle());
        oldBook.setGenre(book.getGenre());
        oldBook.setPublisher(book.getPublisher());
        oldBook.setTheme(book.getTheme());
        return BookModel.toModel(bookRepository.save(oldBook));
    }

    public boolean deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return false;
        }

        bookRepository.deleteById(id);

        return true;
    }
}
