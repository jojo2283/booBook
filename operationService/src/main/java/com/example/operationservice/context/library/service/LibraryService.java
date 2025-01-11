package com.example.operationservice.context.library.service;

import com.example.operationservice.context.book.model.BookCopy;
import com.example.operationservice.context.book.model.BookModelForReport;
import com.example.operationservice.context.book.repository.CopiesRepository;
import com.example.operationservice.context.booktransaction.model.BookTransaction;
import com.example.operationservice.context.booktransaction.repository.BookTransactionRepository;
import com.example.operationservice.context.library.model.LibraryReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final CopiesRepository copiesRepository;
    private final BookTransactionRepository bookTransactionRepository;

    public List<LibraryReportResponse> getReport(Long libraryId, LocalDate date) {
        // Получаем все копии книг для данной библиотеки с их соответствующими данными о книге (например, название, автор)
        List<BookCopy> bookCopies = copiesRepository.findByLibraryId(libraryId);
        List<BookTransaction> bookTransactions = bookTransactionRepository.findAll();
        // Создаем карту для хранения количества копий каждой книги
        Map<Long, Integer> bookCountMap = new HashMap<>();
        if (date == null) {
            // Перебираем все копии книг и подсчитываем количество каждой книги
            for (BookCopy bookCopy : bookCopies) {
                if (bookCopy.getAvailable()) {
                    Long bookId = bookCopy.getBook().getId();
                    bookCountMap.put(bookId, bookCountMap.getOrDefault(bookId, 0) + 1);
                }
            }
        } else {
            for (BookCopy bookCopy : bookCopies) {
                Long bookId = bookCopy.getBook().getId();
                boolean f = Boolean.TRUE;
                for (BookTransaction bookTransaction : bookTransactions) {
                    if (bookTransaction.getBookCopy() == bookCopy) {
                        if (bookTransaction.getBorrowDate() != null) {
                            if (bookTransaction.getBorrowDate().toLocalDate().isBefore(date) && bookTransaction.getReturned() == null) {
                                f = Boolean.FALSE;
                                break;
                            }
                        }
                    }
                }
                if (f) {

                    bookCountMap.put(bookId, bookCountMap.getOrDefault(bookId, 0) + 1);
                }
            }
        }

        // Создаем список ответов для API
        List<LibraryReportResponse> reportResponses = new ArrayList<>();

        // Заполняем список reportResponses данными о книгах и количестве их копий
        for (
                Map.Entry<Long, Integer> entry : bookCountMap.entrySet()) {
            Long bookId = entry.getKey();
            Integer count = entry.getValue();

            // Создаем объект LibraryReportResponse
            LibraryReportResponse response = new LibraryReportResponse();
            response.setCount(count);

            // Найти BookModel для книги (информация о книге уже загружена)
            BookModelForReport bookModel = bookCopies.stream()
                    .filter(bookCopy -> bookCopy.getBook().getId().equals(bookId))
                    .findFirst()
                    .map(BookModelForReport::toModel)
                    .orElseThrow(() -> new RuntimeException("Book not found"));  // Преобразовать в BookModel

            response.setBookModels(bookModel);

            // Добавляем в список
            reportResponses.add(response);
        }

        return reportResponses;
    }
}
