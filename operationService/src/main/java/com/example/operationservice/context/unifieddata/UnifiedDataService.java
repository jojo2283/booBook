package com.example.operationservice.context.unifieddata;

import com.example.operationservice.context.author.model.AuthorModel;
import com.example.operationservice.context.booktransaction.repository.BookTransactionRepository;
import com.example.operationservice.context.rating.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UnifiedDataService {
    private final RatingRepository ratingRepository;
    private final BookTransactionRepository bookTransactionRepository;

    public UnifiedDataService(RatingRepository ratingRepository, BookTransactionRepository bookTransactionRepository) {
        this.ratingRepository = ratingRepository;
        this.bookTransactionRepository = bookTransactionRepository;
    }

    public List<UnifiedData> getUnifiedDataSortedByTime(String userId) {
        List<UnifiedData> unifiedData = new ArrayList<>();

        // Добавляем данные из Rating
        unifiedData.addAll(
                ratingRepository.findAll().stream()
                        .map(rating -> new UnifiedData(
                                rating.getId(),
                                "Rating",
                                rating.getTime(),
                                rating.getUserId(),
                                rating.getRatingValue(),
                                rating.getReview(),
                                rating.getBook().getTitle(),
                                rating.getBook().getAuthors().stream().map(AuthorModel::toModel).collect(Collectors.toList()), // FirstName для Rating отсутствует
                                null,
                                null,
                                null,
                                null
                        ))
                        .toList()
        );

        // Добавляем данные из BookTransaction
        unifiedData.addAll(
                bookTransactionRepository.findAll().stream()
                        .map(transaction -> new UnifiedData(
                                transaction.getId(),
                                "BookTransaction",
                                transaction.getCreationDate(),
                                transaction.getUserId(),
                                null,
                                null,
                                transaction.getBookCopy().getBook().getTitle(),
                                transaction.getBookCopy().getBook().getAuthors().stream().map(AuthorModel::toModel).collect(Collectors.toList()),
                                transaction.getBookCopy().getLibrary(),
                                transaction.getBorrowDate(),
                                transaction.getStatus().toString(),
                                transaction.getComment()
                        ))
                        .toList()
        );
        if (userId == null) {
            // Сортируем объединенный список по времени
            return unifiedData.stream()
                    .sorted(Comparator.comparing(UnifiedData::getTime).reversed())
                    .collect(Collectors.toList());
        }
        return unifiedData.stream()
                .filter(unit -> Objects.equals(unit.getUserId(), userId))
                .sorted(Comparator.comparing(UnifiedData::getTime).reversed())
                .collect(Collectors.toList());
    }


}
