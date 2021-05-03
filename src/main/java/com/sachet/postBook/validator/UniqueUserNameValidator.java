package com.sachet.postBook.validator;

import com.sachet.postBook.custom_constraints.UserNameConstraint;
import com.sachet.postBook.model.User;
import com.sachet.postBook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserNameValidator implements ConstraintValidator<UserNameConstraint, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        User userInDB = userRepository.findByUserName(s);
        if (userInDB == null){
            return true;
        }
        return false;
    }
}
