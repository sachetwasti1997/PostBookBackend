package com.sachet.postBook.rest;

import com.sachet.postBook.model.User;
import com.sachet.postBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/1.0/users/save")
    void createUsers(@RequestBody User user){
        User userSaved = userService.save(user);
    }

}
