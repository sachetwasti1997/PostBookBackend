package com.sachet.postBook.rest;

import com.sachet.postBook.custom_error.ApiError;
import com.sachet.postBook.model.User;
import com.sachet.postBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


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












