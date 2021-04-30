package com.sachet.postBook;

import com.sachet.postBook.model.User;
import com.sachet.postBook.repository.UserRepository;
import com.sachet.postBook.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.function.Supplier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    public static String API_USER = "/api/1.0/users/save";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    private User createValidUser(){
        User user = new User();
        user.setUserName("test_user_name");
        user.setDisplayName("test_display_name");
        user.setPassword("test_password");
        return user;
    }

    @BeforeEach
    public void cleanUp(){
        userService.deleteAll();
    }

    @Test
    public void postUser_userValid_receiveOk(){
        User user = createValidUser();

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USER, user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void postUser_userValid_userSavedToDatabase(){
        User user = createValidUser();

        ResponseEntity<Object> response = testRestTemplate.postForEntity(API_USER, user, Object.class);
        Assertions.assertEquals(userService.count(), 1);
    }

    @Test
    public void postUser_userValid_checkPasswordHashed(){
        User user = createValidUser();
        testRestTemplate.postForEntity(API_USER, user, Object.class);
        List<User> users = userService.findAll();
        User user1 = users.get(0);
        Assertions.assertNotEquals(user1.getPassword(), user.getPassword());
    }

}





















