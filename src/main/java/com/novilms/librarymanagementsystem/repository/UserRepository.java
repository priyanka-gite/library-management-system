package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAllUsersByName(String name);
}
