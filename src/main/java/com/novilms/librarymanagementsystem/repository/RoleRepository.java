package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <Role,String> {
    Optional<Role> findByRoleName(String rolename);
}
