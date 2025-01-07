package com.example.operationservice.context.booktransaction.repository;

import com.example.operationservice.context.booktransaction.model.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction,Long> {

    @Query("SELECT bt FROM BookTransaction bt " +
            "JOIN BookCopy bc ON bt.bookCopyId = bc.id " +
            "WHERE bt.borrowDate IS NULL AND bc.library.id = :libraryId")
    List<BookTransaction> findUnborrowedTransactionsByLibraryId(@Param("libraryId") Long libraryId);
}


