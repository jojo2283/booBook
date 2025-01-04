package com.example.bookservice.context.publisher.service;

import com.example.bookservice.context.publisher.model.Publisher;
import com.example.bookservice.context.publisher.repository.PublisherRepository;
import com.example.bookservice.context.theme.model.Theme;
import com.example.bookservice.context.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public List<Publisher> findAllPublishers() {

        return publisherRepository.findAll();
    }

}
