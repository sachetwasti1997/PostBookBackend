package com.sachet.postBook.configuration;

import com.sachet.postBook.model.User;
import com.sachet.postBook.repository.UserRepository;
import com.sachet.postBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsService responsible for obtaining userDetails from the database
 */
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public AuthDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByEmail(s);
        if (user == null){
            throw new UsernameNotFoundException("No user found with given email!");
        }
        return user;
    }
}
