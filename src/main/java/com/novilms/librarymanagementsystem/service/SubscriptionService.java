package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.SubscriptionDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Subscription;
import com.novilms.librarymanagementsystem.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        subscriptionRepository.save(convertDtoToSubscription(subscriptionDto));
        return subscriptionDto;
    }
    public List<SubscriptionDto> getAllSubscriptions(){
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            subscriptionDtos.add(convertSubscriptionToDto(subscription));
        }
        return subscriptionDtos;
    }

    private SubscriptionDto convertSubscriptionToDto(Subscription subscription) {
        return new SubscriptionDto(subscription.getId(),subscription.getStartDate(),subscription.getEndDate(),subscription.getMaxBookLimit(),subscription.getNumberOfBooksBorrowed(),subscription.getSubscriptionType(), subscription.getUser().getEmail());
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

    public SubscriptionDto getSubscriptions(Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if(subscription.isPresent()){
            return convertSubscriptionToDto(subscription.get());
        } else {
            throw new RecordNotFoundException("Subscription not Found");
        }
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
