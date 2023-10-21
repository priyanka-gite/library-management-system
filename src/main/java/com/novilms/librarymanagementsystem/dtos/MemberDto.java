package com.novilms.librarymanagementsystem.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    @NotNull(message = "member name is required")
    private String name;
    private String address;
    private Date startDateOfMembership;
    private int maxBookLimit;
    private Date endDateOfMembership;
    
}

