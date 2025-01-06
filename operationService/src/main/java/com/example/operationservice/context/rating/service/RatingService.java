package com.example.operationservice.context.rating.service;

import com.example.operationservice.context.rating.model.Rating;
import com.example.operationservice.context.rating.model.RatingModel;
import com.example.operationservice.context.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public List<RatingModel> getAllRatings(){
        return ratingRepository.findAll().stream().map(RatingModel::toModel).collect(Collectors.toList());
    }

    public List<RatingModel> getOneRatings(Long bookId) {
        List<Rating> ratingList = ratingRepository.findAllByBookId(bookId).orElse(null);
        if (ratingList !=null) {
            return ratingList.stream().map(RatingModel::toModel).collect(Collectors.toList());
        }
        return null;
    }
}
