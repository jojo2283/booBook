package com.example.operationservice.context.booktransaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "book_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_copy_id", nullable = false)
    private Integer bookCopyId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "returned", nullable = false)
    private Boolean returned = false;

    private String email;
}
