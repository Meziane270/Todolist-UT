package com.todolist.integration;

import com.todolist.model.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
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
