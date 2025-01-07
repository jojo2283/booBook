package com.example.operationservice.context.booktransaction.service;

import com.example.operationservice.config.JwtTokenUtil;
import com.example.operationservice.context.book.exception.BookCopyNotFoundInLibraryException;
import com.example.operationservice.context.book.model.BookCopy;
import com.example.operationservice.context.book.repository.BookRepository;
import com.example.operationservice.context.book.repository.CopiesRepository;
import com.example.operationservice.context.booktransaction.model.BookTransaction;
import com.example.operationservice.context.booktransaction.repository.BookTransactionRepository;
import com.example.operationservice.context.library.model.Library;
import com.example.operationservice.context.library.model.LibraryRequest;
import com.example.operationservice.context.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final BookTransactionRepository bookTransactionRepository;
    private final BookRepository bookRepository;
    private final CopiesRepository copiesRepository;


    public List<BookTransaction> getAll() {
        return bookTransactionRepository.findAll();
    }

    public BookTransaction reserve(Long id, LibraryRequest library) {
        BookTransaction transaction = new BookTransaction();
//        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        List<BookCopy> bookCopyList = copiesRepository.findByBookIdAndLibraryId(id, library.getLibraryId());
        if (bookCopyList.isEmpty()) {
            throw new BookCopyNotFoundInLibraryException("Book copy not found in Library");
        }
        BookCopy book = bookCopyList.get(0);
        transaction.setBookCopyId(Math.toIntExact(book.getId()));

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = JwtTokenUtil.parseToken(jwt.getTokenValue());
        transaction.setUserId(userDetails.getId());
        transaction.setEmail(userDetails.getEmail());

        return bookTransactionRepository.save(transaction);
    }

    public List<BookTransaction> getRequests(Long libraryId) {
        return bookTransactionRepository.findUnborrowedTransactionsByLibraryId(libraryId);
    }
}
