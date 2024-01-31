package com.novilms.librarymanagementsystem.dtos;

import jakarta.validation.constraints.NotEmpty;

public record RoleDto(@NotEmpty(message = "Role name start cannot be empty")String roleName) {
}
