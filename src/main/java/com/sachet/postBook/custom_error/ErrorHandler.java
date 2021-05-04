package com.sachet.postBook.custom_error;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.Map;

/**
 * ErrorController is custom controller, spring internally redirects unhandled exceptions to it so that we can process
 * the fail response
 */
@RestController
@SuppressWarnings("deprecated")
public class ErrorHandler implements ErrorController {

    private ErrorAttributes errorAttributes;

    @Autowired
    public ErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public ApiError HandleError(WebRequest webRequest){
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));

        String message = (String) attributes.get("message");
        Date date = (Date) attributes.get("timestamp");
        int status = (Integer) attributes.get("status");

        ApiError apiError = new ApiError(status, message);
        apiError.setTimeStamp(date.getTime());
        return apiError;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
