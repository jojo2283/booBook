package com.example.operationservice.context.booktransaction.service;

import com.example.operationservice.config.JwtTokenUtil;
import com.example.operationservice.context.book.exception.BookCopyNotFoundInLibraryException;
import com.example.operationservice.context.book.model.BookCopy;
import com.example.operationservice.context.book.repository.BookRepository;
import com.example.operationservice.context.book.repository.CopiesRepository;
import com.example.operationservice.context.booktransaction.model.BookTransaction;
import com.example.operationservice.context.booktransaction.model.BookTransactionModel;
import com.example.operationservice.context.booktransaction.model.TransactionResponse;
import com.example.operationservice.context.booktransaction.repository.BookTransactionRepository;
import com.example.operationservice.context.library.model.LibraryRequest;
import com.example.operationservice.context.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        transaction.setBookCopy(book);

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = JwtTokenUtil.parseToken(jwt.getTokenValue());
        transaction.setUserId(userDetails.getId());
        transaction.setEmail(userDetails.getEmail());
        transaction.setFirstName(userDetails.getFirstName());
        transaction.setLastName(userDetails.getLastName());
        return bookTransactionRepository.save(transaction);
    }

    public List<TransactionResponse> getRequests(Long libraryId) {
        List<BookTransaction> res = bookTransactionRepository.findUnborrowedTransactionsByLibraryId(libraryId);

        return res.stream().map(TransactionResponse::fromBookTransToResponse).collect(Collectors.toList());
    }

    public BookTransactionModel approve(Long id) {
        BookTransaction transaction = bookTransactionRepository.findById(id).orElse(null);
        transaction.setBorrowDate(LocalDateTime.now());
        BookCopy bookCopy = copiesRepository.findById(transaction.getBookCopy().getId()).orElse(null);
        if (bookCopy != null && bookCopy.getAvailable() == Boolean.TRUE) {
            bookCopy.setAvailable(Boolean.FALSE);
            copiesRepository.save(bookCopy);
        }
        return BookTransactionModel.toModel(bookTransactionRepository.save(transaction));

    }
}
