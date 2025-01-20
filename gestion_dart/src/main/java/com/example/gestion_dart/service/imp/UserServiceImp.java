package com.example.gestion_dart.service.imp;

import com.example.gestion_dart.entity.User;
import com.example.gestion_dart.repository.UserRepository;
import com.example.gestion_dart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }




    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public void editUser(Long id, User editedUser) {
        User existingUser = userRepository.getById(id);

        existingUser.setUsername(editedUser.getUsername());
        existingUser.setEmail(editedUser.getEmail());
        existingUser.setAge(editedUser.getAge());

        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public List<User> getallUser() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getallUserWithoutAdmin() {
        return userRepository.findUsersWithoutAdmin();
    }
}
