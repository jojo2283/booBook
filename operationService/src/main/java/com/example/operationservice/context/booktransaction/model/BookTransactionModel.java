package com.example.operationservice.context.booktransaction.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookTransactionModel {
    private Long id;
    private Long BookCopyId;
    private String userId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private Boolean returned;
    private String email;
    private String firstName;
    private String lastName;

    public static BookTransactionModel toModel(BookTransaction bookTransaction) {
        BookTransactionModel model = new BookTransactionModel();
        model.setId(bookTransaction.getId());
        model.setBookCopyId(bookTransaction.getBookCopy().getId());
        model.setUserId(bookTransaction.getUserId());
        model.setBorrowDate(bookTransaction.getBorrowDate());
        model.setReturnDate(bookTransaction.getReturnDate());
        model.setReturned(bookTransaction.getReturned());
        model.setEmail(bookTransaction.getEmail());
        model.setFirstName(bookTransaction.getFirstName());
        model.setLastName(bookTransaction.getLastName());
        return model;
    }
}
