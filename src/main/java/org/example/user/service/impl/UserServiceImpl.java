package org.example.user.service.impl;

import org.example.user.model.User;
import org.example.user.repository.UserRepository;
import org.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        if (id == null) throw new IllegalArgumentException("id is null");

        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user with following id is not found"));
    }

    @Override
    public User save(User user) {
        if (user == null) throw new IllegalArgumentException("user is null");

        User newUser = User
                .builder()
                .name(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public User updateById(Long id, User user) {
        if(id == null || user == null) throw new IllegalArgumentException("id or user is null");

        User userForUpdate = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user with following id is not found"));

        userForUpdate.setName(user.getName());
        userForUpdate.setPassword(user.getPassword());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setRole(user.getRole());

        return userRepository.save(userForUpdate);
    }

    @Override
    public void deleteById(Long id) {
        if(id == null) throw new IllegalArgumentException("id is null");

        if(!userRepository.existsById(id)) {
            throw new NoSuchElementException("user with following id is not found");
        }

        userRepository.deleteById(id);
    }
}
