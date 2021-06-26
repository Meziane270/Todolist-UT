package com.todolist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.model.Item;
import com.todolist.model.User;
import com.todolist.repository.ItemRepository;
import com.todolist.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ItemIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getExistantItemById() throws Exception {
        User user = new User("testExistItem@mail.com", "firstTest", "lastTest", "passTest", LocalDate.now());
        user.getTodoList().addItem(new Item("new item", "test content"));
        userRepository.save(user);
        long id = user.getTodoList().getItems().get(0).getId();
        this.mockMvc.perform(get("/item/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getInexistantItemById() throws Exception {
        this.mockMvc.perform(get("/item/{id}", 0))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void updateItemWithValidData() throws Exception {
        User user = new User("test@mail.com", "firstTest", "lastTest", "passTest", LocalDate.now());
        user.getTodoList().addItem(new Item("new item", "test content"));
        userRepository.save(user);
        Item item = user.getTodoList().getItems().get(0);
        Item newUpdatedItem = new Item("updated Name", "Valid content");
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
        user.getTodoList().addItem(new Item("new item", "test content", new Timestamp(System.currentTimeMillis() - 32 * 60000)));
        userRepository.save(user);
        Item item = user.getTodoList().getItems().get(0);
        Item newUpdatedItem = new Item("", "");
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

    @Test
    public void deleteExistingItem() throws Exception {
        Item item = itemRepository.save(new Item("new item", "test content"));
        this.mockMvc.perform(delete("/item/{id}", item.getId()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void deleteInexistantItem() throws Exception {
        this.mockMvc.perform(delete("/item/{id}", 0))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
