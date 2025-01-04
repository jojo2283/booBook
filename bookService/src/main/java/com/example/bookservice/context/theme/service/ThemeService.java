package com.example.bookservice.context.theme.service;

import com.example.bookservice.context.genre.model.Genre;
import com.example.bookservice.context.genre.repository.GenreRepository;
import com.example.bookservice.context.theme.model.Theme;
import com.example.bookservice.context.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    private final ThemeRepository themeRepository;

    public List<Theme> findAllThemes() {

        return themeRepository.findAll();
    }

}
