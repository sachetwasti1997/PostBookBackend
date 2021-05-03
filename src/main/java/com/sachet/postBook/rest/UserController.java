package com.sachet.postBook.rest;

import com.sachet.postBook.model.User;
import com.sachet.postBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/1.0/users/save")
    public ResponseEntity<?> createUsers(@Valid @RequestBody User user){

        User userSaved = userService.save(user);
        return new ResponseEntity<>(userSaved, HttpStatus.OK);
    }

}
