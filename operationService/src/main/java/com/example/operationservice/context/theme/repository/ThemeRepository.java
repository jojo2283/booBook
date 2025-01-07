package com.example.operationservice.context.theme.repository;

import com.example.operationservice.context.theme.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme,Long> {
}
