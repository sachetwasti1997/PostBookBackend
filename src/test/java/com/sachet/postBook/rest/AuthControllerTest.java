package com.sachet.postBook.rest;

import com.sachet.postBook.custom_error.ApiError;
import com.sachet.postBook.model.User;
import com.sachet.postBook.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    private static final String API_1_0_LOGIN = "/api/1.0/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    private final UserService userService;

    @Autowired
    public AuthControllerTest(UserService userService) {
        this.userService = userService;
    }

    public <T> ResponseEntity<T> login(Class<T> response){
        return testRestTemplate.postForEntity(API_1_0_LOGIN, null, response);
    }

    public User createValidUser(){
        User user = new User();
        user.setDisplayName("test_user");
        user.setUserName("test_user");
        user.setEmail("testuser@gmail.com");
        user.setPassword("P4assword");
        user.setImage("profile-image.png");
        return user;
    }

    @BeforeEach
    public void clean(){
        userService.deleteAll();
        testRestTemplate.getRestTemplate().getInterceptors().clear();
    }

    @Test
    public void postLogin_withoutUserCredentials_receiveUnAuthorised(){
        ResponseEntity<Object> responseEntity = login(Object.class);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_withIncorrectUserCredentials_receiveUnAuthorised(){
        authenticate();
        ResponseEntity<Object> responseEntity = login(Object.class);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void postLogin_withIncorrectUserCredentials_receiveUnAuthorisedWithoutWWWAuthenticateHeader(){
        authenticate();
        ResponseEntity<Object> responseEntity = login(Object.class);
        Assertions.assertEquals(responseEntity.getHeaders().containsKey("WWW-Authenticate"), false);
    }

    @Test
    public void postLogin_withoutUserCredentials_receiveApiError(){
        ResponseEntity<ApiError> responseEntity = login(ApiError.class);
        Assertions.assertEquals(responseEntity.getBody().getStatusCode(), HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void postLogin_withoutUserCredentials_receiveApiErrorWithoutValidationError(){
        ResponseEntity<String> responseEntity = login(String.class);
        Assertions.assertEquals(responseEntity.getBody().contains("validationErrors"), false);
    }

    @Test
    public void postLogin_withValidCredentials_receiveOk(){
        User user = createValidUser();
        userService.save(user);
        authenticate();

        ResponseEntity<?> responseEntity = login(Object.class);
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void postLogin_withValidCredential_receiveLoggedInUserId(){
        User user = createValidUser();
        User inDB = userService.save(user);
        authenticate();

        ResponseEntity<?> responseEntity = login(Object.class);
        System.out.println(responseEntity.getBody());
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        Integer loggedInId = (Integer) body.get("id");
        Assertions.assertEquals(loggedInId, inDB.getId());
    }

    @Test
    public void postLogin_withValidCredential_receiveLoggedInUsersImage(){
        User user = createValidUser();
        User inDB = userService.save(user);
        authenticate();

        ResponseEntity<?> responseEntity = login(Object.class);
        System.out.println(responseEntity.getBody());
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        String loggedInUserImage = (String) body.get("image");
        Assertions.assertEquals(loggedInUserImage, inDB.getImage());
    }

    @Test
    public void postLogin_withValidCredential_receiveLoggedInUsersDisplayName(){
        User user = createValidUser();
        User inDB = userService.save(user);
        authenticate();

        ResponseEntity<?> responseEntity = login(Object.class);
        System.out.println(responseEntity.getBody());
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        String loggedInUserDisplayName = (String) body.get("displayName");
        Assertions.assertEquals(loggedInUserDisplayName, inDB.getDisplayName());
    }

    @Test
    public void postLogin_withValidCredential_receiveLoggedInUsersUserName(){
        User user = createValidUser();
        User inDB = userService.save(user);
        authenticate();

        ResponseEntity<?> responseEntity = login(Object.class);
        System.out.println(responseEntity.getBody());
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        String loggedInUserDisplayName = (String) body.get("userName");
        Assertions.assertEquals(loggedInUserDisplayName, inDB.getDisplayName());
    }

    @Test
    public void postLogin_withValidCredential_receiveLoggedInUserWithoutPassword(){
        User user = createValidUser();
        User inDB = userService.save(user);
        authenticate();

        ResponseEntity<?> responseEntity = login(Object.class);
        System.out.println(responseEntity.getBody());
        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        Assertions.assertEquals(body.containsKey("password"), false);
    }

    private void authenticate() {
        testRestTemplate.getRestTemplate().getInterceptors().add(new BasicAuthenticationInterceptor("testuser@gmail.com", "P4assword"));
    }

}

























