package com.novilms.librarymanagementsystem.dtos;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.SubscriptionType;
import com.novilms.librarymanagementsystem.model.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;


public record SubscriptionDto (Long id, LocalDate startDate, LocalDate endDate, int maxBookLimit, int numberOfBooksBorrowed, SubscriptionType subscriptionType, User user) {

}

