package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveRepository extends JpaRepository<Reservation,Long> {
}
