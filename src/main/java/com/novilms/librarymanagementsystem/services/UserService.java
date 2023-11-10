package com.novilms.librarymanagementsystem.services;

import com.novilms.librarymanagementsystem.dtos.UserDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.User;
import com.novilms.librarymanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(convertUserToDto(user));
        }
        return userDtoList;
    }

    public List<UserDto> getUsersByName(String name) {
        List<User> userList = userRepository.findAllUsersByName(name);
        return convertUserListToDtoList(userList);
    }

    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDto userDto = convertUserToDto(user.get());
            return userDto;
        } else {
            throw new RecordNotFoundException("User not Found");
        }
    }

    public UserDto addUser(UserDto userDto) {
        userRepository.save(convertDtoToUser(userDto));
        return userDto;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        if (!userRepository.existsById(id)) {
            throw new RecordNotFoundException("Book Not Found");
        }
        User updateUser = userRepository.findById(id).orElse(null);
        updateUser.setUsername(userDto.username());
        updateUser.setAddress(userDto.address());
        updateUser.setEmail(userDto.email());
        updateUser.setMobileNumber(userDto.mobileNumber());
        updateUser.setRole(userDto.role());
        updateUser.setReservations(userDto.reservations());
        updateUser.setSubscription(userDto.subscription());
        userRepository.save(updateUser);
        return userDto;
    }


    //    ---------------CONVERSIONS--------------------
    public UserDto convertUserToDto(User user) {
        UserDto UserDto = new UserDto(user.getId(), user.getUsername(), user.getAddress(), user.getEmail(), user.getMobileNumber(), user.getRole(), user.getReservations(), user.getSubscription());
        return UserDto;
    }

    public User convertDtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setAddress(userDto.address());
        user.setEmail(userDto.email());
        user.setMobileNumber(userDto.mobileNumber());
        user.setRole(userDto.role());
        user.setReservations(userDto.reservations());
        user.setSubscription(userDto.subscription());
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
