package com.sachet.postBook.service;

import com.sachet.postBook.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findUserById(Long id);

    List<User> findAll();

    User update(Long id, User user);

    User deleteById(Long id);

    User delete(User user);

    void deleteAll();

    long count();

}
