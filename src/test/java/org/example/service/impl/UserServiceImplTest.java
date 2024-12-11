package org.example.service.impl;

import org.example.user.model.Role;
import org.example.user.model.User;
import org.example.user.repository.UserRepository;
import org.example.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void test_getAll_shouldReturnAllUsers() {
        User firstUser = User
                .builder()
                .id(1L)
                .name("First User")
                .email("first@email.com")
                .password("password")
                .role(Role.ADMIN)
                .build();
        User secondUser = User
                .builder()
                .id(2L)
                .name("Second User")
                .email("second@email.com")
                .password("password")
                .role(Role.USER)
                .build();

        when(userRepository.findAll()).thenReturn(List.of(firstUser, secondUser));

        Assertions.assertEquals(List.of(firstUser, secondUser), userServiceImpl.getAll());
        verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void test_getUserById_shouldReturnUser_WhenUserExists() {

        Long userId = 1L;

        User user = User
                .builder()
                .id(1L)
                .name("user")
                .email("user@email.com")
                .password("password")
                .role(Role.ADMIN)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));

        Assertions.assertEquals(user, userServiceImpl.getById(userId));
        verify(userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    void test_save_shouldSaveUser_WhenUserBodyIsNotNull() {

        User request = User
                .builder()
                .name("user")
                .email("user@email.com")
                .password("password")
                .role(Role.ADMIN)
                .build();

        when(userRepository.save(request)).thenReturn(request);

        Assertions.assertEquals(request, userServiceImpl.save(request));
        verify(userRepository, Mockito.times(1)).save(request);
    }

    @Test
    void test_updateById_shouldUpdateUser_WhenUserBodyIsNotNullAndUserIsExists() {
        Long userId = 1L;

        User existingUser = User.builder()
                .id(userId)
                .name("user")
                .email("user@email.com")
                .password("password")
                .role(Role.ADMIN)
                .build();

        User updatedUser = User.builder()
                .id(userId)
                .name("UPDATED")
                .email("user@email.com")
                .password("password")
                .role(Role.ADMIN)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userServiceImpl.updateById(userId, updatedUser);

        Assertions.assertEquals("UPDATED", result.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void test_deleteById_shouldDeleteUser_WhenUserBodyIsNotNull() {
        Long userId = 1L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        userServiceImpl.deleteById(userId);

        Mockito.verify(userRepository).deleteById(userId);
    }

}
