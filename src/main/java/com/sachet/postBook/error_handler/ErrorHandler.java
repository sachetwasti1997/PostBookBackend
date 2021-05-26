package com.sachet.postBook.error_handler;

import com.sachet.postBook.custom_error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> forbiddenErrorHandler(RuntimeException ex, WebRequest request){
        Map<String, Object> errors = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));
        String message = (String) errors.get("message");
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        System.out.println(errors);
        if (message.equals("Access is denied")){
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(new ApiError(status.value(), message), status);
    }

}
