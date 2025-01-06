package com.example.operationservice.context.rating.controller;


import com.example.operationservice.api.Endpoints;
import com.example.operationservice.context.rating.model.Rating;
import com.example.operationservice.context.rating.model.RatingModel;
import com.example.operationservice.context.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoints.REVIEWS)
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingModel>> getAllRatings(){
        return ResponseEntity.ok(ratingService.getAllRatings());
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<List<RatingModel>> getRatingsByBook(@PathVariable Long bookId){
        return ResponseEntity.ok(ratingService.getOneRatings(bookId));
    }
}
