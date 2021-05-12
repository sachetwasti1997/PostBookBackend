package com.sachet.postBook.rest;

import com.sachet.postBook.model.AuthenticationRequest;
import com.sachet.postBook.model.AuthenticationResponse;
import com.sachet.postBook.service.service_interface.UserService;
import com.sachet.postBook.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/api/1.0/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
            authenticate(userDetails, authenticationRequest.getPassword());
            final String token = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.NOT_FOUND);
        }
    }

    private void authenticate(UserDetails userDetails, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, password));
        }catch (Exception e){
            throw new Exception("Invalid Credentials!");
        }
    }

}
