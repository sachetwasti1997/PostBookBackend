package com.sachet.postBook.repository;

import com.sachet.postBook.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;//alternative to the standered entity manager, for testing purpose

    @Autowired
    UserRepository userRepository;

    @Test
    public void findByUserName_whenUserExists_returnsUser(){

        User user = new User();
        user.setUserName("test_user_name");
        user.setDisplayName("test_display_name");
        user.setPassword("test_password4V@");
        user.setEmail("test@gmail.com");

        testEntityManager.persist(user);

        User inDB = userRepository.findByUserName("test_user_name");
        assertNotNull(inDB);

    }

    @Test
    public void findByUserName_userNotExist_returnNull(){
        User inDB = userRepository.findByUserName("test_user_name");
        assertNull(inDB);
    }

}

















