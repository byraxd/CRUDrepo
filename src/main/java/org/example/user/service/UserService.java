package org.example.user.service;

import org.example.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(Long id);

    User save(User user);

    User updateById(Long id, User user);

    void deleteById(Long id);
}
