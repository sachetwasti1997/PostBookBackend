package com.sachet.postBook.service.service_interface;

import com.sachet.postBook.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);

    List<User> findAll();

    User update(Long id, User user);

    User deleteById(Long id);

    User delete(User user);

    void deleteAll();

    long count();

}
