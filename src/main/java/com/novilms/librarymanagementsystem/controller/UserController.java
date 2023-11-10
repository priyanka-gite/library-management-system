package com.novilms.librarymanagementsystem.controller;


import com.novilms.librarymanagementsystem.dtos.UserDto;
import com.novilms.librarymanagementsystem.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "name", required = false) Optional<String> name) {
        List<UserDto> dtos;
        if (name.isEmpty()) {
            dtos = userService.getAllUsers();
        } else {
            dtos = userService.getUsersByName(name.get());
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUSerById(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserDto userDto) {

        UserDto dto = userService.addUser(userDto);

        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

//    TODO :This is breaking
    @PutMapping("{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDto newUser) {
        UserDto dto = userService.updateUser(id, newUser);
        return ResponseEntity.ok().body(dto);
    }


}
