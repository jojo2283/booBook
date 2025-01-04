package com.example.bookservice.context.genre.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.genre.model.Genre;
import com.example.bookservice.context.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.GENRES)
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> allGenres() {
        return ResponseEntity.status(HttpStatus.OK).body(genreService.findAllGenres());
    }


}
