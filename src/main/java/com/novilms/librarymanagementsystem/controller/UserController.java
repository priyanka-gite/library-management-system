package com.novilms.librarymanagementsystem.controller;


import com.novilms.librarymanagementsystem.dtos.UserDto;
import com.novilms.librarymanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "username", required = false) Optional<String> username) {
        List<UserDto> dtos;
        if (username.isEmpty()) {
            dtos = userService.getAllUsers();
        } else {
            dtos = userService.getUsersByUsername(username.get());
        }

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> addUser( @Valid @RequestBody UserDto userDto) {

        UserDto dto = userService.addUser(userDto);

        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>("User with username: " + username + " deleted", HttpStatus.OK);
    }

}
