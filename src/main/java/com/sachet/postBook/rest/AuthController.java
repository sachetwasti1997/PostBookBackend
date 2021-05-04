package com.sachet.postBook.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.sachet.postBook.custom_constraints.CurrentUser;
import com.sachet.postBook.customize.Views;
import com.sachet.postBook.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

    @PostMapping("/api/1.0/login")
    @JsonView(Views.Base.class)
    ResponseEntity<?> handleLogin(@CurrentUser User loggedInUser){
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }

    /**
     * The exception handler to interscept the error as done in user controller doesn't work here.
     * This is because spring security is handling this error in its internal security filter chain.
     * This filter chain is taking long before the request is actually reaching to the controller, since the request is
     * not passed to the controller, the exceptionHandler will not be triggered
     */

}
