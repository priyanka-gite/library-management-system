package com.novilms.librarymanagementsystem.dtos;

import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.Role;
import com.novilms.librarymanagementsystem.model.Subscription;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserDto(Long id, @NotEmpty(message = "Username cannot be empty ") String username, @NotEmpty(message = "Password cannot be empty ") String password, @NotEmpty(message = "Address cannot be empty ") String address, @NotEmpty(message = "Email cannot be empty ") String email, @NotEmpty(message = "MObile number cannot be empty ") String mobileNumber, @NotEmpty(message = "User roles cannot be empty ") Set<RoleDto> roles, Set<ReservationDto> reservations, SubscriptionDto subscription) {

    public UserDto {
        if (username.isEmpty() && password.isEmpty()){
            throw new IllegalArgumentException("Username and Password required");
        }
    }
}
