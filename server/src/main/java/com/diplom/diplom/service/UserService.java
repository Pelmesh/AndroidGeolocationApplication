package com.diplom.diplom.service;

import com.diplom.diplom.model.User;

public interface UserService {

    User save(User user);

    User findByName(String username);

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

}
