package com.novilms.librarymanagementsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novilms.librarymanagementsystem.dtos.AuthorDto;
import com.novilms.librarymanagementsystem.security.JwtService;
import com.novilms.librarymanagementsystem.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(AuthorController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private AuthorService authorServices;
    @MockBean
    private JwtService jwtService;

    @Test
    void getAllAuthors() throws Exception {
        when(authorServices.getAllAuthors()).thenReturn(List.of(new AuthorDto(100L,"Author","male","email", Collections.singleton("isbn"))));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].publishedBookIsbn").value("isbn"));
    }

    @Test
    void getAuthorById() throws Exception {
        when(authorServices.getAuthorById(100L)).thenReturn(new AuthorDto(100L,"Author","male","email", Collections.singleton("isbn")));
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/author/100"))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishedBookIsbn").value("isbn"));
    }

    @Test
    void addAuthor() throws Exception {
        AuthorDto authorDto = new AuthorDto(null,"name","male","email", Set.of());
        when(authorServices.addAuthor(authorDto)).thenReturn(new AuthorDto(100L,"name","male","email", Set.of("isbn")));
        this.mockMvc.perform(post("/author")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishedBookIsbn").value("isbn"));
    }

    @Test
    void deleteAuthor() throws Exception {
        this.mockMvc.perform(delete("/author/100"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void updateAuthor() throws Exception {
        AuthorDto authorDto = new AuthorDto(100l,"name","male","email", Set.of("isbn"));
        when(authorServices.updateAuthor(100l, authorDto)).thenReturn(new AuthorDto(100L,"name","male","email", Set.of("isbn")));
        this.mockMvc.perform(put("/author/100")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authorDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}