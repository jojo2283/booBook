package com.example.operationservice.context.rating.service;

import com.example.operationservice.config.JwtTokenUtil;
import com.example.operationservice.context.book.repository.BookRepository;
import com.example.operationservice.context.rating.model.Rating;
import com.example.operationservice.context.rating.model.RatingModel;
import com.example.operationservice.context.rating.repository.RatingRepository;
import com.example.operationservice.context.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;

    public List<RatingModel> getAllRatings() {
        return ratingRepository.findAll().stream().map(RatingModel::toModel).collect(Collectors.toList());
    }

    public List<RatingModel> getOneRatings(Long bookId) {
        List<Rating> ratingList = ratingRepository.findAllByBookId(bookId).orElse(null);
        if (ratingList != null) {
            return ratingList.stream().map(RatingModel::toModel).collect(Collectors.toList());
        }
        return null;
    }

    public RatingModel createReview(RatingModel rating) throws NoSuchAlgorithmException, InvalidKeySpecException {


        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = JwtTokenUtil.parseToken(jwt.getTokenValue());
        Rating newRating = new Rating();
        newRating.setRatingValue(rating.getRatingValue());
        newRating.setReview(rating.getReview());
        newRating.setUserId(userDetails.getId());
        newRating.setBook(bookRepository.findById(rating.getBookId()).orElseThrow());
        return RatingModel.toModel(ratingRepository.save(newRating));
    }
}
