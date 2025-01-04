package com.example.bookservice.context.author.service;


import com.example.bookservice.context.author.model.Author;
import com.example.bookservice.context.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> findAllGenres() {

        return authorRepository.findAll();
    }
}
