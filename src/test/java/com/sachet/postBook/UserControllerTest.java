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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        user.setPassword("test_password4V@");
        user.setEmail("test@gmail.com");
        return user;
    }

    @BeforeEach
    public void cleanUp(){
        userService.deleteAll();
    }

    public <T> ResponseEntity<?> postSignUp(Object request, Class<T> response){
        return testRestTemplate.postForEntity(API_USER, request, response);
    }

    @Test
    public void postUser_userValid_receiveOk(){
        User user = createValidUser();

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void postUser_userValid_userSavedToDatabase(){
        User user = createValidUser();

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(userService.count(), 1);
    }

    @Test
    public void postUser_userInvalid_userNameNull(){
        User user = createValidUser();
        user.setUserName(null);

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_displayNameNull(){
        User user = createValidUser();
        user.setDisplayName(null);

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_passwordNull(){
        User user = createValidUser();
        user.setPassword(null);

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_emailNull(){
        User user = createValidUser();
        user.setEmail(null);

        ResponseEntity<?> responseEntity = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void postUser_userValid_checkPasswordHashed(){
        User user = createValidUser();
        postSignUp(user, Object.class);
        List<User> users = userService.findAll();
        User user1 = users.get(0);
        Assertions.assertNotEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    public void postUser_userInvalid_userNameLengthLess(){
        User user = createValidUser();
        user.setUserName("abc");

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_displayNameLengthLess(){
        User user = createValidUser();
        user.setDisplayName("abc");

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_userNameLengthMore(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setDisplayName(valueOf256Chars);

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_displayNameLengthMore(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setDisplayName(valueOf256Chars);

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_passwordLengthLess(){
        User user = createValidUser();
        user.setPassword("aA1]");

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_passwordLengthMore(){
        User user = createValidUser();
        String valueOf256Chars = IntStream.rangeClosed(1, 256).mapToObj(x -> "a").collect(Collectors.joining());
        user.setPassword(valueOf256Chars+"aA1@");

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userInvalid_passwordWithAllLowerCase(){
        User user = createValidUser();
        user.setPassword("aaaaaaa");

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postUser_userValid_passwordCorrect(){
        User user = createValidUser();
        user.setPassword("aAA1@");

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}





















