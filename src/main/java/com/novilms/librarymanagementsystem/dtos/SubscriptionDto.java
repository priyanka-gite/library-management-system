package com.novilms.librarymanagementsystem.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.SubscriptionType;
import com.novilms.librarymanagementsystem.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;


public record SubscriptionDto (Long id, @NotNull(message = "Subscription start date cannot be empty") @JsonFormat(pattern="yyyy-MM-dd") LocalDate startDate, @NotNull(message = "Subscription end date cannot be empty ") @JsonFormat(pattern="yyyy-MM-dd") LocalDate endDate, int maxBookLimit, int numberOfBooksBorrowed, @NotNull(message = "Subscription type cannot be empty") SubscriptionType subscriptionType, @NotEmpty(message = "User email cannot be empty ") String userEmail) {

}
