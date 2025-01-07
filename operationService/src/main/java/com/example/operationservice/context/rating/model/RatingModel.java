package com.example.operationservice.context.rating.model;

import com.example.operationservice.config.JwtTokenUtil;
import com.example.operationservice.context.book.model.BookModel;
import com.example.operationservice.context.user.CustomUserDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RatingModel {

    private Long id;
    private String userId;
    private BookModel book;
    private Integer ratingValue;
    private String review;
    private Long bookId;
    private LocalDateTime time;

    public static RatingModel toModel(Rating rating) {

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails userDetails = JwtTokenUtil.parseToken(jwt.getTokenValue());

        RatingModel model = new RatingModel();
        model.setId(rating.getId());
        model.setUserId(userDetails.getId());
        model.setBook(BookModel.toModel(rating.getBook()));
        model.setRatingValue(rating.getRatingValue());
        model.setReview(rating.getReview());
        model.setTime(rating.getTime());
        return model;
    }

}
