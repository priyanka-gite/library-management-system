package com.novilms.librarymanagementsystem.dtos;

public record AuthenticationDto(String email, String password) {

    public AuthenticationDto {
        if (email.isEmpty() && password.isEmpty()){
            throw new IllegalArgumentException("Username and Password required");
        }
    }
}
