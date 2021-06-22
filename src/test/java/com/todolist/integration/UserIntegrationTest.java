package com.todolist.integration;

import com.todolist.repository.UserRepository;
import com.todolist.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private long userId;

    @BeforeAll
    public void setup() {
        User user = new User("test@mail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        userId = user.getId();
    }

    @Test
    public void createNewValidUser() throws Exception {
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"email\" : \"post@mail.com\",\n" +
                        "  \"firstname\" : \"postTest\",\n" +
                        "  \"lastname\" : \"postTest\",\n" +
                        "  \"password\" : \"test123\"\n" +
                        " \"birthDate\" : \"2021-06-11\"\n"+
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("post@mail.com"))
                .andExpect(jsonPath("$.firstname").value("postTest"))
                .andExpect(jsonPath("$.lastname").value("postTest"))
                .andExpect(jsonPath("$.password").value("test123"));
    }

    @Test
    public void getAllUsers() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getUserById() throws Exception {
        this.mockMvc.perform(get("/user/{id}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@mail.com"))
                .andExpect(jsonPath("$.firstname").value("firstname"))
                .andExpect(jsonPath("$.lastname").value("lastname"))
                .andExpect(jsonPath("$.password").value("password123"))
                .andReturn();
    }

    @Test
    public void updateUser() throws Exception {
        this.mockMvc.perform(put("/user/{id}", userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/user/{id}", userId))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

}