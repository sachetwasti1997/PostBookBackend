package com.sachet.postBook.service;

import com.sachet.postBook.model.MyUserDetails;
import com.sachet.postBook.model.User;
import com.sachet.postBook.service.service_interface.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("User with that email doesn't exist");
        }
        return new MyUserDetails(user);
    }
}
