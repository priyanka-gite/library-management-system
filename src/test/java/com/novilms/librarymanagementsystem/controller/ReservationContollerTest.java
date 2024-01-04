package com.novilms.librarymanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ReservationContoller.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class ReservationContollerTest {

    @Autowired
    MockMvc mockMvc;

}