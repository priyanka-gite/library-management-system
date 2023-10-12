package com.novilms.librarymanagementsystem.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "date-of-membership")
    private Date dateOfMembership;
    @Column(name = "max-book-limit")
    private int maxBookLimit;

    public Member(Long id, String name, String address, Date dateOfMembership, int maxBookLimit) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dateOfMembership = dateOfMembership;
        this.maxBookLimit = maxBookLimit;
    }

    public Member() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfMembership() {
        return dateOfMembership;
    }

    public void setDateOfMembership(Date dateOfMembership) {
        this.dateOfMembership = dateOfMembership;
    }

    public int getMaxBookLimit() {
        return maxBookLimit;
    }

    public void setMaxBookLimit(int maxBookLimit) {
        this.maxBookLimit = maxBookLimit;
    }
}
