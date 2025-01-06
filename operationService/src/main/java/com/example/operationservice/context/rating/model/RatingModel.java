package com.example.operationservice.context.rating.model;

import com.example.operationservice.context.book.model.Book;
import com.example.operationservice.context.book.model.BookModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingModel {

    private Long id;
    private String userId;
    private BookModel book;
    private Integer ratingValue;
    private String review;

    public static RatingModel toModel(Rating rating){
        RatingModel model = new RatingModel();
        model.setId(rating.getId());
        model.setBook(BookModel.toModel(rating.getBook()));
        model.setRatingValue(rating.getRatingValue());
        model.setReview(rating.getReview());
        return model;
    }

}
