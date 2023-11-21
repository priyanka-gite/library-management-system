package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.Role;
import com.novilms.librarymanagementsystem.model.Subscription;
import java.util.Set;

public record UserDto(Long id, String username, String password, String address, String email, String mobileNumber, Set<RoleDto> roles, Set<ReservationDto> reservations, SubscriptionDto subscription) {

    public UserDto {
        if (username.isEmpty() && password.isEmpty()){
            throw new IllegalArgumentException("Username and Password required");
        }
    }
}
