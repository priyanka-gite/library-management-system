package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.RoleDto;
import com.novilms.librarymanagementsystem.dtos.UserDto;
import com.novilms.librarymanagementsystem.exceptions.BusinessException;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Role;
import com.novilms.librarymanagementsystem.model.User;
import com.novilms.librarymanagementsystem.repository.RoleRepository;
import com.novilms.librarymanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(convertUserToDto(user));
        }
        return userDtoList;
    }

    public List<UserDto> getUsersByUsername(String username) {
        List<User> user = userRepository.findAllUsersByUsername(username);
        return convertUserListToDtoList(user);
    }

//TODO use this method in controller
    public UserDto getUsersByEmail(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        return convertUserToDto(userByEmail.get());
    }


    public UserDto addUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.email());
        if(!existingUser.isEmpty()) {
            throw new BusinessException("User with username " + userDto.email() + "already exist");
        }
        userRepository.save(convertDtoToUser(userDto));
        return userDto;
    }




    //    ---------------CONVERSIONS--------------------
    public UserDto convertUserToDto(User user) {
        UserDto UserDto = new UserDto(user.getId(), user.getUsername(), user.getPassword(),user.getAddress(), user.getEmail(), user.getMobileNumber(), user.getRoles().stream().map(r -> new RoleDto(r.getRoleName())).collect(Collectors.toSet()), Collections.emptySet(), null);
        return UserDto;
    }

    public User convertDtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setAddress(userDto.address());
        user.setEmail(userDto.email());
        user.setMobileNumber(userDto.mobileNumber());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        //convert roledDto to roles
        user.setRoles(userDto.roles().stream().map(r -> roleRepository.findByRoleName(r.roleName()).orElseThrow(() -> new RecordNotFoundException("Invalid role: " + r.roleName() + " specified"))).collect(Collectors.toSet()));
        //TODO fix this
        //user.setReservations(userDto.reservations());
        //user.setSubscription(userDto.subscription());
        return user;
    }

    private List<UserDto> convertUserListToDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto dto = convertUserToDto(user);
            userDtoList.add(dto);
        }
        return userDtoList;
    }

}