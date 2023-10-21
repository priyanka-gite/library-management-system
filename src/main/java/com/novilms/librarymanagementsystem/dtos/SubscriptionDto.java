package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Long id;
    private Date startDateOfSubscription;
    private Date endDateOfSubscription;
    private SubscriptionType subscriptionType;
}

