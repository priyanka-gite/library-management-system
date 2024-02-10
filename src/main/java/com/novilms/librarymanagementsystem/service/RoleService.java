package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.RoleDto;
import com.novilms.librarymanagementsystem.model.Role;
import com.novilms.librarymanagementsystem.repository.RoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    //--- get all roles ---//
    public List<RoleDto> getAllRoles() {

        List<RoleDto> roleDtoList = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            RoleDto roleDto = new RoleDto(role.getRoleName());
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }
}
