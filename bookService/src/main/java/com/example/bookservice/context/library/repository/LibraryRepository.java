package com.example.bookservice.context.library.repository;

import com.example.bookservice.context.library.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library,Long> {
}
