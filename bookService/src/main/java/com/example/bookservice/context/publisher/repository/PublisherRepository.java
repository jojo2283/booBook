package com.example.bookservice.context.publisher.repository;

import com.example.bookservice.context.publisher.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
