package com.sachet.postBook;

import com.sachet.postBook.custom_error.ApiError;
import com.sachet.postBook.model.AuthenticationResponse;
import com.sachet.postBook.model.MyUserDetails;
import com.sachet.postBook.model.User;
import com.sachet.postBook.service.service_interface.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    public static String API_USER_CREATE = "/user/api/1.0/signup";
    private static final String API_USER_GET = "/user/api/1.0/";
    private static final String API_USER_GET_AUTH = "/user/api/1.0/token";

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

    public <T> ResponseEntity<T> postSignUp(Object request, Class<T> response){
        return testRestTemplate.postForEntity(API_USER_CREATE, request, response);
    }

    public <T> ResponseEntity<T> getUser(Long id, Class<T> response){
        return testRestTemplate.getForEntity(API_USER_GET+id, response);
    }

    public <T> ResponseEntity<T> getUserWithAuthorizationHeaders(Long id, String authorizationToken, Class<T> response){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+authorizationToken);

        HttpEntity<?> request = new HttpEntity<>(headers);
        return testRestTemplate.exchange(API_USER_GET+id, HttpMethod.GET, request, response);
    }

    public <T> ResponseEntity<T> getUserFromAuthentication(String token, Class<T> response){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+token);

        HttpEntity<?> request = new HttpEntity<>(headers);
        return testRestTemplate.exchange(API_USER_GET_AUTH, HttpMethod.GET, request, response);
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
    public void postUser_whenUserIsInvalid_receiveApiError(){
        User user = new User();
        ResponseEntity<ApiError> responseEntity = postSignUp(user, ApiError.class);
        System.out.println(responseEntity.getBody().getMessage());
        Assertions.assertEquals(responseEntity.getBody().getValidationErrors().size(), 4);
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

    @Test
    public void postUser_whenAnotherUserHasSameUsername_receiveBadRequest(){
        userService.save(createValidUser());

        User user = createValidUser();
        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void postUser_userInvalid_emailInvalid(){
        User user = createValidUser();
        user.setEmail("sachetgmail.com");

        ResponseEntity<?> response = postSignUp(user, Object.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getUser_withId_receiveUnauthorised(){

        User user = createValidUser();
        User userCreated = userService.save(user);

        ResponseEntity<?> responseEntity = getUser(userCreated.getId(), ApiError.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

    }

    @Test
    public void getUser_withId_tokenInHeader_receiveOk(){

        User user = createValidUser();
        ResponseEntity<?> responseEntity = postSignUp(user, Object.class);

        user = userService.findUserByEmail(user.getEmail());

        String token = (String) ((Map<String, Object>)responseEntity.getBody()).get("jsonWebToken");

        ResponseEntity<?> responseEntity1 = getUserWithAuthorizationHeaders(user.getId(), token, Object.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
    }

    @Test
    public void getUser_withToken_receiveOk(){
        User user = createValidUser();
        ResponseEntity<?> responseEntity = postSignUp(user, Object.class);

        String token = (String) ((Map<String, Object>)responseEntity.getBody()).get("jsonWebToken");

        ResponseEntity<?> responseEntity1 = getUserFromAuthentication(token, Object.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity1.getStatusCode());
    }

}





















