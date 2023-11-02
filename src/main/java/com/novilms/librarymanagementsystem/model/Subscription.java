package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;
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

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "subscription_type")
    private SubscriptionType subscriptionType;
    @Column(name = "max_book_limit")
    private int maxBookLimit;
    @Column(name = "number_of_books_borrowed")
    private int numberOfBooksBorrowed;

    @OneToOne(mappedBy = "subscription")
    private User user;
}
