package com.example.operationservice.context.book.repository;

import com.example.operationservice.context.book.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CopiesRepository extends JpaRepository< BookCopy,Long> {

    List<BookCopy> findByBookIdAndLibraryId(Long bookId, Long libraryId);
    List<BookCopy> findByLibraryId(Long libraryId);
    List<BookCopy> findByBookId(Long bookId);

    BookCopy findByInventoryNumber(String inventoryNumber);

}
