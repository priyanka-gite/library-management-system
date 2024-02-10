package com.novilms.librarymanagementsystem.dtos;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(@NotNull(message = "User email cannot be epmty") String email, @NotNull(message = "User password cannot be epmty") String password) {
}
