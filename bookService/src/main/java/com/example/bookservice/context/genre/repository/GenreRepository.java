package com.example.bookservice.context.genre.repository;

import com.example.bookservice.context.genre.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "genres")
public interface GenreRepository  extends JpaRepository<Genre, Long> {
}
