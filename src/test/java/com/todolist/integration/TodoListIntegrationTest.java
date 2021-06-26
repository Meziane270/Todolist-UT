package com.todolist.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.model.Item;
import com.todolist.model.User;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
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
    public void getAllTodolist() throws Exception {
        User user = new User("getAll1@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();
        User user2 = new User("getAll2@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        userRepository.save(user2);
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
        Timestamp timestampItem1 = new Timestamp(System.currentTimeMillis() - 32 * 60000);
        Timestamp timestampItem2 = new Timestamp(System.currentTimeMillis());

        Item savedItem = new Item("post@gmail.com", "post1", timestampItem1);

        user.getTodoList().addItem(savedItem);
        userRepository.save(user);

        Item itemCreatedAfterThirtyMinutes = new Item("post2@gmail.com", "post2", timestampItem2);

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

        Timestamp timestampItem1 = new Timestamp(System.currentTimeMillis() - 29 * 60000);
        Timestamp timestampItem2 = new Timestamp(System.currentTimeMillis());

        Item savedItem = new Item("post@gmail.com", "post1", timestampItem1);

        user.getTodoList().addItem(savedItem);
        userRepository.save(user);

        Item itemCreatedAfterThirtyMinutes = new Item("post2@gmail.com", "post2", timestampItem2);

        this.mockMvc.perform(post("/todo-list/{id}/item", todoListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreatedAfterThirtyMinutes))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addItemToFullList() throws Exception {
        User user = new User("addItem@gmail.com", "firstname", "lastname", "password123", LocalDate.now());
        user = userRepository.save(user);
        todoListId = user.getTodoList().getId();
        for (int i = 0; i < 10; i++) {
            try {
                user.getTodoList().addItem(new Item("posti" + i + "@gmail.com", "post" + i, new Timestamp(System.currentTimeMillis() + (30 * i) * 60000)));
            } catch (Exception ignored) {
            }
        }
        userRepository.save(user);
        Item itemCreatedAfterThirtyMinutes = new Item("post2@gmail.com", "post2", new Timestamp(System.currentTimeMillis() + (30 * 10) * 60000));
        this.mockMvc.perform(post("/todo-list/{id}/item", todoListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemCreatedAfterThirtyMinutes))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
