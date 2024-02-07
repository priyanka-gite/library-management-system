package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.SubscriptionDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Subscription;
import com.novilms.librarymanagementsystem.model.User;
import com.novilms.librarymanagementsystem.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, @Lazy UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
    }

    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto) {
        return convertSubscriptionToDto(subscriptionRepository.save(convertDtoToSubscription(subscriptionDto)));
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
        User user = userService.getUser(subscriptionDto.userEmail());
        user.setSubscription(subscription);
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
        return convertSubscriptionToDto(updateSubscription);
    }

    public SubscriptionDto getSubscriptions(Long id) {
        return convertSubscriptionToDto(getSubscription(id));
    }

    public Subscription getSubscription(Long id) {
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        if(subscription.isPresent()){
            return subscription.get();
        } else {
            throw new RecordNotFoundException("Subscription not Found");
        }
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
