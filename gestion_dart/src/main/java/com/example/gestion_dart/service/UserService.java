package com.example.gestion_dart.service;

import com.example.gestion_dart.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    void editUser(Long id, User editedUser);

    void deleteUser(Long id);

    List<User> getallUser();
    List<User> getallUserWithoutAdmin();
}
