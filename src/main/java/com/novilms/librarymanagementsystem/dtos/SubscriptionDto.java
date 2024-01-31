package com.novilms.librarymanagementsystem.dtos;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.SubscriptionType;
import com.novilms.librarymanagementsystem.model.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;


public record SubscriptionDto (Long id, @NotEmpty(message = "Subscription start date cannot be empty") LocalDate startDate, @NotEmpty(message = "Subscription end date cannot be empty ") LocalDate endDate, int maxBookLimit, int numberOfBooksBorrowed, @NotEmpty(message = "Subscription type cannot be empty") SubscriptionType subscriptionType, User user) {

}

