package com.example.bookservice.context.genre.service;


import com.example.bookservice.context.genre.model.Genre;
import com.example.bookservice.context.genre.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> findAllGenres() {

        return genreRepository.findAll();
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }


    public boolean deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id).orElse(null);

        if (genre == null) {
            return false;
        }

        genreRepository.deleteById(id);

        return true;
    }
}
