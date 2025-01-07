package com.example.operationservice.context.booktransaction.controller;

import com.example.operationservice.api.Endpoints;
import com.example.operationservice.context.booktransaction.model.BookTransactionModel;
import com.example.operationservice.context.booktransaction.model.TransactionResponse;
import com.example.operationservice.context.booktransaction.service.TransactionService;
import com.example.operationservice.context.library.model.LibraryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoints.TRANSACTION)
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllRequests(@RequestParam Long libraryId) {
        return ResponseEntity.ok(transactionService.getRequests(libraryId));
    }

    @PostMapping("/books/{id}/reserve")
    public ResponseEntity<BookTransactionModel> reservBook(@PathVariable Long id, @RequestBody LibraryRequest library) {
        return ResponseEntity.ok(transactionService.reserve(id, library));
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<BookTransactionModel> approveRequest(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.approve(id));
    }


}
