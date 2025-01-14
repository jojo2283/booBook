package com.example.operationservice.context.unifieddata;

import com.example.operationservice.config.JwtTokenUtil;
import com.example.operationservice.context.author.model.AuthorModel;
import com.example.operationservice.context.booktransaction.repository.BookTransactionRepository;
import com.example.operationservice.context.rating.repository.RatingRepository;
import com.example.operationservice.context.user.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public List<UnifiedData> getUnifiedDataSortedByTime(String email) {
        List<UnifiedData> unifiedData = new ArrayList<>();
        String userId;

            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomUserDetails userDetails = JwtTokenUtil.parseToken(jwt.getTokenValue());
            userId = userDetails.getId();


        unifiedData.addAll(
                ratingRepository.findAll().stream()
                        .map(rating -> new UnifiedData(
                                rating.getId(),
                                "Rating",
                                rating.getTime(),
                                rating.getUserId(),
                                rating.getEmail(),
                                rating.getRatingValue(),
                                rating.getReview(),
                                rating.getBook().getTitle(),
                                rating.getBook().getAuthors().stream().map(AuthorModel::toModel).collect(Collectors.toList()), // FirstName для Rating отсутствует
                                null,
                                null,
                                null,
                                null,
                                null
                        ))
                        .toList()
        );


        unifiedData.addAll(
                bookTransactionRepository.findAll().stream()
                        .map(transaction -> new UnifiedData(
                                transaction.getId(),
                                "BookTransaction",
                                transaction.getCreationDate(),
                                transaction.getUserId(),
                                transaction.getEmail(),
                                null,
                                null,
                                transaction.getBookCopy().getBook().getTitle(),
                                transaction.getBookCopy().getBook().getAuthors().stream().map(AuthorModel::toModel).collect(Collectors.toList()),
                                transaction.getBookCopy().getLibrary(),
                                transaction.getBorrowDate(),
                                transaction.getStatus().toString(),
                                transaction.getComment(),
                                transaction.getBookCopy().getInventoryNumber()
                        ))
                        .toList()
        );
        if (userId == null&&email==null) {
            // Сортируем объединенный список по времени
            throw new RuntimeException();
        }
        if (email!=null) {
            return unifiedData.stream()
                    .filter(unit -> Objects.equals(unit.getEmail(), email))
                    .sorted(Comparator.comparing(UnifiedData::getTime).reversed())
                    .collect(Collectors.toList());
        }

        return unifiedData.stream()
                .filter(unit -> Objects.equals(unit.getUserId(), userId))
                .sorted(Comparator.comparing(UnifiedData::getTime).reversed())
                .collect(Collectors.toList());
    }


}
