package com.sachet.postBook.validator;

import com.sachet.postBook.custom_constraints.EmailConstraint;
import com.sachet.postBook.model.User;
import com.sachet.postBook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<EmailConstraint, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findByEmail(s);
        return user == null;
    }
}
