package com.example.bookservice.context.publisher.controller;

import com.example.bookservice.api.Endpoints;
import com.example.bookservice.context.publisher.model.Publisher;
import com.example.bookservice.context.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(Endpoints.PUBLISHER)
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<Publisher>> allPublishers() {
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.findAllPublishers());
    }


}
