package com.sachet.postBook.repository;

import com.sachet.postBook.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
    User findByEmail(String email);

    Page<User> findAllByEmailNot(String email, Pageable pageable);

}
