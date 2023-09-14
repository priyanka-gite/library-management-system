package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
}
