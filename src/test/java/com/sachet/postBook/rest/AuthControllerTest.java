package com.sachet.postBook.rest;

import com.sachet.postBook.model.AuthenticationRequest;
import com.sachet.postBook.service.service_interface.UserService;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("auth-test")
class AuthControllerTest {

    private static final String AUTH_API = "/auth/api/1.0/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    UserService userService;

    public <T> ResponseEntity<T> postLogin(AuthenticationRequest request, Class<T> response){
        return testRestTemplate.postForEntity(AUTH_API, request, response);
    }

    public AuthenticationRequest createAuthRequest(){
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUserName("test@gmail.com");
        authenticationRequest.setPassword("test_password4V@");

        return authenticationRequest;
    }

    @Test
    public void logUser_validCredential_receiveOk(){
        AuthenticationRequest authenticationRequest = createAuthRequest();

        ResponseEntity<?> responseEntity = postLogin(authenticationRequest, Object.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void logUser_invalidCredential_receiveForbidden(){
        AuthenticationRequest authenticationRequest = createAuthRequest();
        authenticationRequest.setPassword("4aAAAAAAA");

        ResponseEntity<?> responseEntity = postLogin(authenticationRequest, Object.class);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

//    @AfterEach
//    public void cleanUp(){
//        userService.deleteAll();
//    }

}
























