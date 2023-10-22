package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Member;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Long id;
    private Date startDateOfSubscription;
    private Date endDateOfSubscription;
    private SubscriptionType subscriptionType;
    private int maxBookLimit;
    private int numberOfBooksBorrowed;
    private Member member;
    private List<Reservation> reservationList = new ArrayList<>();
}

