package com.example.operationservice.context.rating.repository;

import com.example.operationservice.context.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<List<Rating>> findAllByBookId(Long bookId);
}
