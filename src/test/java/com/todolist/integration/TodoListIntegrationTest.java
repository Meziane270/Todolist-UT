package com.todolist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.model.Item;
import com.todolist.repository.UserRepository;
import com.todolist.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoListIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private long todoListId;


    @Test
    public void getByIdTodoListTest() throws Exception {
        User user = new User("test@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();
        this.mockMvc.perform(get("/todo-list/{id}", todoListId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andReturn();

    }

    @Test
    public void getAllTodolist() throws  Exception {
        User user = new User("getAll1@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();
        User user2 = new User("getAll2@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user2 = userRepository.save(user2);

        this.mockMvc.perform(get("/todo-list", todoListId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].user.email", is("getAll1@gmail.com")))
                .andExpect(jsonPath("$[1].user.email", is("getAll2@gmail.com")))
                .andReturn();
    }

    @Test
    public void addItem() throws Exception {
        User user = new User("addItem@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();
        Item newItem = new Item();
        newItem.setName("post@mail.com");
        newItem.setContent("postTest");

        this.mockMvc.perform(post("/todo-list/{id}/item", todoListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newItem))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andReturn();
    }

    @Test
    public void addInvalidItem() throws Exception {
        User user = new User("addItem@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();
        Item newItem = new Item();
        newItem.setName("post@mail.com");

        this.mockMvc.perform(post("/todo-list/{id}/item", todoListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newItem))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addItemAfterThirtyMinutesCoolDown() throws Exception {
        User user = new User("addItem@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();
        Timestamp timestampItem1 = new Timestamp(System.currentTimeMillis()-32*60000);
        Timestamp timestampItem2 = new Timestamp(System.currentTimeMillis());
        Item savedItem = new Item();
        savedItem.setName("post@gmail.com");
        savedItem.setContent("post1");
        savedItem.setCreationDate(timestampItem1);

        user.getTodoList().addItem(savedItem);
        user = userRepository.save(user);

        System.out.println(user.getTodoList().getItems().get(0).getCreationDate());
        Item itemCreatedAfterThirtyMinutes = new Item();
        itemCreatedAfterThirtyMinutes.setName("post2@gmail.com");
        itemCreatedAfterThirtyMinutes.setContent("post2");
        itemCreatedAfterThirtyMinutes.setCreationDate(timestampItem2);

        this.mockMvc.perform(post("/todo-list/{id}/item", todoListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreatedAfterThirtyMinutes))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addItemBeforeThirtyMinutesCoolDown() throws Exception {
        User user = new User("addItem@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();

        Item savedItem = new Item();
        savedItem.setName("post@mail.com");
        savedItem.setContent("post1");

        user.getTodoList().addItem(savedItem);
        userRepository.save(user);

        Item itemCreatedAfterThirtyMinutes = new Item();
        itemCreatedAfterThirtyMinutes.setName("post2@mail.com");
        itemCreatedAfterThirtyMinutes.setContent("post2");

        this.mockMvc.perform(post("/todo-list/{id}/item", todoListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreatedAfterThirtyMinutes))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
