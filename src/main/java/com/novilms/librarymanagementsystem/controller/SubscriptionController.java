package com.novilms.librarymanagementsystem.controller;


import com.novilms.librarymanagementsystem.dtos.SubscriptionDto;
import com.novilms.librarymanagementsystem.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions(){
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }
    @GetMapping("{id}")
    public ResponseEntity<SubscriptionDto> getAllSubscriptions(@PathVariable Long id){
        return ResponseEntity.ok(subscriptionService.getSubscriptions(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SubscriptionDto> deleteSubscriptions(@PathVariable Long id){
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        SubscriptionDto dto = subscriptionService.createSubscription(subscriptionDto);
        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSubscription(@PathVariable Long id, @Valid @RequestBody SubscriptionDto newSubscription) {
        SubscriptionDto dto = subscriptionService.updateSubscription(id, newSubscription);
        return ResponseEntity.ok().body(dto);
    }
}
