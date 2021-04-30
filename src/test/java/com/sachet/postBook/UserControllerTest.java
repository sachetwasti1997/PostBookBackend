package com.sachet.postBook;

import com.sachet.postBook.model.User;
import com.sachet.postBook.repository.UserRepository;
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

import java.util.function.Supplier;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    public static String API_USER = "/api/1.0/users";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserRepository userRepository;

    private User createValidUser(){
        User user = new User();
        user.setUserName("test_user_name");
        user.setDisplayName("test_display_name");
        user.setPassword("test_password");
        return user;
    }

    @BeforeEach
    public void cleanUp(){
        userRepository.deleteAll();
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
        Assertions.assertEquals(userRepository.count(), 1);
    }

}





















