package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.Role;
import com.novilms.librarymanagementsystem.model.Subscription;
import lombok.Getter;

import java.util.Set;

public record UserDto(Long id, String username, String address, String email, String mobileNumber, Set<Role> role, Set<Reservation> reservations, Subscription subscription) {
}
