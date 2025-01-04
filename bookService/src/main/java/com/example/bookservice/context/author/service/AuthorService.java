package com.example.bookservice.context.author.service;


import com.example.bookservice.context.author.model.AuthorModel;
import com.example.bookservice.context.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorModel> findAllAuthors() {

        return authorRepository.findAll().stream().map(AuthorModel::toModel).collect(Collectors.toList());
    }
}
