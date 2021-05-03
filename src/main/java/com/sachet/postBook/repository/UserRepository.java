package com.sachet.postBook.repository;

import com.sachet.postBook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
    User findByEmail(String email);

}
