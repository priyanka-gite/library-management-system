package com.novilms.librarymanagementsystem.services;

import com.novilms.librarymanagementsystem.dtos.SubscriptionDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Subscription;
import com.novilms.librarymanagementsystem.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        subscriptionRepository.save(convertDtoToSubscription(subscriptionDto));
        return subscriptionDto;

    }

    private Subscription convertDtoToSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription();
        subscription.setStartDate(subscriptionDto.startDate());
        subscription.setEndDate(subscriptionDto.endDate());
        subscription.setMaxBookLimit(subscriptionDto.maxBookLimit());
        subscription.setNumberOfBooksBorrowed(subscriptionDto.numberOfBooksBorrowed());
        subscription.setSubscriptionType(subscriptionDto.subscriptionType());
        subscription.setUser(subscription.getUser());
        return subscription;
    }

    public SubscriptionDto updateSubscription(Long id, SubscriptionDto subscriptionDto) {
        if (!subscriptionRepository.existsById(id)) {
            throw new RecordNotFoundException("Subscription Not Found");
        }
        Subscription updateSubscription = subscriptionRepository.findById(id).orElse(null);
        updateSubscription.setStartDate(subscriptionDto.startDate());
        updateSubscription.setEndDate(subscriptionDto.endDate());
        updateSubscription.setSubscriptionType(subscriptionDto.subscriptionType());
        updateSubscription.setMaxBookLimit(subscriptionDto.maxBookLimit());
        updateSubscription.setNumberOfBooksBorrowed(subscriptionDto.numberOfBooksBorrowed());
        return subscriptionDto;
    }
}
