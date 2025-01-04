package com.example.bookservice.context.theme.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.theme.model.Theme;
import com.example.bookservice.context.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.THEME)
public class ThemeController {
    private final ThemeService themeService;

    @GetMapping
    public ResponseEntity<List<Theme>> allThemes() {
        return ResponseEntity.status(HttpStatus.OK).body(themeService.findAllThemes());
    }


}
