package com.example.bookservice.context.book.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookSearchRequest {
    private List<String> authors;
    private List<String> genres;
    private List<String> topics;
    private List<String> publishers;
    private Integer minCopies;
    private Integer maxCopies;
    private String status;
    private String name;
    private String popularity; // "asc" или "desc"
    private Double rating;
}
