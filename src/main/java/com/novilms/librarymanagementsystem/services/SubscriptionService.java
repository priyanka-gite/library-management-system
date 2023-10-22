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
        subscription.setStartDateOfSubscription(subscriptionDto.getStartDateOfSubscription());
        subscription.setEndDateOfSubscription(subscriptionDto.getEndDateOfSubscription());
        subscription.setSubscriptionType(subscription.getSubscriptionType());
        subscription.setMaxBookLimit(subscriptionDto.getMaxBookLimit());
        subscription.setNumberOfBooksBorrowed(subscriptionDto.getNumberOfBooksBorrowed());
        subscription.setMember(subscriptionDto.getMember());
        subscription.setReservationList(subscription.getReservationList());
        return subscription;
    }

    public SubscriptionDto updateSubscription(Long id, SubscriptionDto subscriptionDto) {
        if (!subscriptionRepository.existsById(id)) {
            throw new RecordNotFoundException("Subscription Not Found");
        }
        Subscription updateSubscription = subscriptionRepository.findById(id).orElse(null);
        updateSubscription.setStartDateOfSubscription(subscriptionDto.getStartDateOfSubscription());
        updateSubscription.setEndDateOfSubscription(subscriptionDto.getEndDateOfSubscription());
        updateSubscription.setSubscriptionType(subscriptionDto.getSubscriptionType());
        updateSubscription.setMaxBookLimit(subscriptionDto.getMaxBookLimit());
        updateSubscription.setNumberOfBooksBorrowed(subscriptionDto.getNumberOfBooksBorrowed());
        updateSubscription.setMember(subscriptionDto.getMember());
        updateSubscription.setReservationList(subscriptionDto.getReservationList());
        return subscriptionDto;
    }
}
