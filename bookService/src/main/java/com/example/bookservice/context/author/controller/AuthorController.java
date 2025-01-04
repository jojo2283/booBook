package com.example.bookservice.context.author.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.author.model.Author;
import com.example.bookservice.context.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.AUTHORS)
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> allGenres() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.findAllGenres());
    }


}
