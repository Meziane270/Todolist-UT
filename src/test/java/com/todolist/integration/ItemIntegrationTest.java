package com.todolist.integration;

import com.todolist.model.Item;
import com.todolist.model.TodoList;
import com.todolist.model.User;
import com.todolist.repository.TodoListRepository;
import com.todolist.repository.UserRepository;
import com.todolist.service.UserService;
import com.todolist.service.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @BeforeEach
    public void setup() {
        User user = new User("test@mail.com", "firstTest", "lastTest", "passTest", LocalDate.now());
        TodoList todoList = user.getTodoList();
        todoList.addItem(new Item("new item", "test"));
    }

    @Test
    public void createNewValidUser() {
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("/user", new User("test@mail.com", "firstname", "lastname", "password123", LocalDate.now()), User.class);
        User user = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(user);
        assertEquals("firstname", user.getFirstname());
    }


}
