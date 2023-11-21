package com.novilms.librarymanagementsystem.controller;


import com.novilms.librarymanagementsystem.dtos.SubscriptionDto;
import com.novilms.librarymanagementsystem.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Object> createSubscription(SubscriptionDto subscriptionDto) {
        SubscriptionDto dto = subscriptionService.createSubscription(subscriptionDto);
        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDto newSubscription) {
        SubscriptionDto dto = subscriptionService.updateSubscription(id, newSubscription);
        return ResponseEntity.ok().body(dto);
    }
}
