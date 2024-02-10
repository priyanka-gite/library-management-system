package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.RoleDto;
import com.novilms.librarymanagementsystem.dtos.UserDto;
import com.novilms.librarymanagementsystem.exceptions.BusinessException;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Reservation;
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
    private final ReservationService reservationService;
    private final SubscriptionService subscriptionService;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(convertUserToDto(user));
        }
        return userDtoList;
    }

    public UserDto getUsersByEmail(String email) {
        return convertUserToDto(getUser(email));
    }

    public void deleteUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RecordNotFoundException("User with username: " + email + " not found");
        }
        userRepository.delete(optionalUser.get());
    }

    public UserDto addUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.email());
        if(!existingUser.isEmpty()) {
            throw new BusinessException("User with username " + userDto.email() + "already exist");
        }
        userRepository.save(convertDtoToUser(userDto));
        return userDto;
    }
    // Not all user details can be updated.
    public UserDto updateUser(String email, UserDto userDto) {
        User user = getUser(email);
        user.setAddress(userDto.address());
        user.setMobileNumber(userDto.mobileNumber());
        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : userDto.roles()) {
            Optional<Role> roleOpt = roleRepository.findByRoleName(roleDto.roleName());
            if(!roleOpt.isPresent()) {
                throw new RecordNotFoundException("Invalid role: " + roleDto.roleName() + " specified");
            }
            Role role = roleOpt.get();
            roles.add(role);
        }
        user.setRoles(roles);
        return convertUserToDto(userRepository.save(convertDtoToUser(userDto)));
    }

    public User getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) {
            throw new RecordNotFoundException("User with email " + email + "not found.");
        }
        return user.get();
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
        Set<Role> roles = new HashSet<>();
        for (RoleDto roleDto : userDto.roles()) {
            Optional<Role> roleOpt = roleRepository.findByRoleName(roleDto.roleName());
            if(!roleOpt.isPresent()) {
                throw new RecordNotFoundException("Invalid role: " + roleDto.roleName() + " specified");
            }
            Role role = roleOpt.get();
            roles.add(role);
        }
        user.setRoles(roles);
        if (userDto.reservationIds() != null) {
            Set<Reservation> reservations = new HashSet<>();
            for (Long res : userDto.reservationIds()) {
                reservations.add(reservationService.getReservation(res));
            }
            user.setReservations(reservations);
        }
        if(userDto.subscriptionId() != null) {
            user.setSubscription(subscriptionService.getSubscription(userDto.subscriptionId()));
        }
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
