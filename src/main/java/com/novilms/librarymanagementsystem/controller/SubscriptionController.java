package com.novilms.librarymanagementsystem.controller;


import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.dtos.SubscriptionDto;
import com.novilms.librarymanagementsystem.services.SubscriptionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Object> createSubscription(SubscriptionDto subscriptionDto) {
        SubscriptionDto dto = subscriptionService.createSubscription(subscriptionDto);
        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.getId()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("subscription/{id}")
    public ResponseEntity<Object> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDto newSubscription) {
        SubscriptionDto dto = subscriptionService.updateSubscription(id, newSubscription);
        return ResponseEntity.ok().body(dto);
    }
}