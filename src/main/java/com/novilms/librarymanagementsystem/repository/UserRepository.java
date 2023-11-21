package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
        List<User> findAllUsersByUsername(String username);
        Optional<User> findByEmail(String email);
}
