package com.diplom.diplom.service;

import com.diplom.diplom.model.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    User findByName(String username);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    Optional<User> findById(Long id);
}
