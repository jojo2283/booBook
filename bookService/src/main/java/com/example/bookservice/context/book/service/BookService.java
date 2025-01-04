package com.example.bookservice.context.book.service;

import com.example.bookservice.context.book.model.Book;
import com.example.bookservice.context.book.model.BookModel;
import com.example.bookservice.context.book.model.BookSearchRequest;
import com.example.bookservice.context.book.repository.BookRepository;
import com.example.bookservice.context.book.specification.BookSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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

        return bookRepository.findAll(spec).stream()
                .map(BookModel::toModel).collect(Collectors.toList());
    }

}
