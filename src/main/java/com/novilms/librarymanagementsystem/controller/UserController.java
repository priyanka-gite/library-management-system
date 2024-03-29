package com.novilms.librarymanagementsystem.controller;


import com.novilms.librarymanagementsystem.dtos.UserDto;
import com.novilms.librarymanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{email}")
    public ResponseEntity<UserDto> getByUsersName(@PathVariable(value = "email") String email) {
        return ResponseEntity.ok(userService.getUsersByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser( @Valid @RequestBody UserDto userDto) {
        UserDto dto = userService.addUser(userDto);
        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{email}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String email, @Valid @RequestBody UserDto userDto) {
        UserDto dto = userService.updateUser(email, userDto);
        return ResponseEntity.ok().body(dto);
    }


}
