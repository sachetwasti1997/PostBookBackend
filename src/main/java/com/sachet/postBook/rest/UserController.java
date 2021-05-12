package com.sachet.postBook.rest;

import com.sachet.postBook.custom_error.ApiError;
import com.sachet.postBook.model.AuthenticationResponse;
import com.sachet.postBook.model.MyUserDetails;
import com.sachet.postBook.model.User;
import com.sachet.postBook.service.service_interface.UserService;
import com.sachet.postBook.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/1.0/signup")
    public ResponseEntity<?> createUsers(@Valid @RequestBody User user){
        User userSaved = userService.save(user);
        final MyUserDetails myUserDetails = new MyUserDetails(userSaved);
        final String token = jwtUtil.generateToken(myUserDetails);
        return new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }

    @GetMapping("/api/1.0/{id}")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long id){
        User user = userService.findUserById(id);
        if (user == null){
            return new ResponseEntity<>(new ApiError(404, "User not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/api/1.0/token")
    public ResponseEntity<?> getUserFromToken(Authentication authentication, Principal principal){
        String email = authentication.getName();
        User user = userService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * when validation error occurs the validator is targeting the `MethodArgumentNotValidException`
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        ApiError apiError = new ApiError(400, "Validation Error");
        BindingResult bindingResult = exception.getBindingResult();
        Map<String , String > validationErrors = new HashMap<>();
        for (FieldError fieldError: bindingResult.getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }

}












