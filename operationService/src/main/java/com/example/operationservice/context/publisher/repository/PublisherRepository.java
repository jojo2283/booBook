package com.example.operationservice.context.publisher.repository;

import com.example.operationservice.context.publisher.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
