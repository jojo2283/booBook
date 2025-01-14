package com.example.operationservice.context.rating.service;

import com.example.operationservice.config.JwtTokenUtil;
import com.example.operationservice.context.book.repository.BookRepository;
import com.example.operationservice.context.rating.model.Rating;
import com.example.operationservice.context.rating.model.RatingModel;
import com.example.operationservice.context.rating.repository.RatingRepository;
import com.example.operationservice.context.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;

    @Transactional
    public List<RatingModel> getAllRatings() {
        return ratingRepository.findAll().stream().map(RatingModel::toModel).collect(Collectors.toList());
    }

    @Transactional
    public List<RatingModel> getOneRatings(Long bookId) {
        List<Rating> ratingList = ratingRepository.findAllByBookId(bookId).orElse(null);
        if (ratingList != null) {
            return ratingList.stream().map(RatingModel::toModel).collect(Collectors.toList());
        }
        return null;
    }

    @Transactional

    public RatingModel createReview(RatingModel rating) {


        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = JwtTokenUtil.parseToken(jwt.getTokenValue());
//        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rating newRating = new Rating();

        newRating.setRatingValue(rating.getRatingValue());
        newRating.setReview(rating.getReview());
        newRating.setUserId(userDetails.getId());
        newRating.setEmail(userDetails.getEmail());
        newRating.setBook(bookRepository.findById(rating.getBookId()).orElseThrow());
        newRating.setTime(LocalDateTime.now());
        return RatingModel.toModel(ratingRepository.save(newRating));
    }
}
