package com.todolist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.model.Item;
import com.todolist.model.User;
import com.todolist.repository.TodoListRepository;
import com.todolist.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ItemIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void updateItemWithValidData() throws Exception {
        User user = new User("test@mail.com", "firstTest", "lastTest", "passTest", LocalDate.now());
        user.getTodoList().addItem(new Item("new item", "test content"));
        userRepository.save(user);
        Item item = user.getTodoList().getItems().get(0);
        Item newUpdatedItem = new Item();
        newUpdatedItem.setName("updated Name");
        newUpdatedItem.setContent("Valid content");
        this.mockMvc.perform(put("/item/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUpdatedItem)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateItemWithInvalidData() throws Exception {
        User user = new User("testInvalid@mail.com", "firstTest", "lastTest", "passTest", LocalDate.now());
        user.getTodoList().addItem(new Item("new item", "test content"));
        userRepository.save(user);
        Item item = user.getTodoList().getItems().get(0);
        Item newUpdatedItem = new Item();
        newUpdatedItem.setName("");
        newUpdatedItem.setContent("");
        this.mockMvc.perform(put("/item/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUpdatedItem)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateItemWithEmptyData() throws Exception {
        User user = new User("testInvalidEmpty@mail.com", "firstTest", "lastTest", "passTest", LocalDate.now());
        user.getTodoList().addItem(new Item("new item", "test content"));
        userRepository.save(user);
        Item item = user.getTodoList().getItems().get(0);
        Item newUpdatedItem = new Item();
        this.mockMvc.perform(put("/item/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUpdatedItem)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
