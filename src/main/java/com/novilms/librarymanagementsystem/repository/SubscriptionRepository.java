package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
}
