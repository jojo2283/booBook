package com.example.bookservice.context.publisher.service;

import com.example.bookservice.context.publisher.model.Publisher;
import com.example.bookservice.context.publisher.repository.PublisherRepository;
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

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public boolean deletePublisher(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElse(null);

        if (publisher == null) {
            return false;
        }

        publisherRepository.deleteById(id);

        return true;
    }
}
