package com.example.bookservice.context.theme.service;

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

    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    public boolean deleteTheme(Long id) {
        Theme theme = themeRepository.findById(id).orElse(null);

        if (theme == null) {
            return false;
        }

        themeRepository.deleteById(id);

        return true;
    }
}
