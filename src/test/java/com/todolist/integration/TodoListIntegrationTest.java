package com.todolist.integration;

import com.todolist.repository.UserRepository;
import com.todolist.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;

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
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

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

        this.mockMvc.perform(post("/todo-list/{id}/item", todoListId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"post@mail.com\",\n" +
                        "  \"content\" : \"postTest\"\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andReturn();
    }

}
