package com.journal.journal.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.journal.entity.User;
import com.journal.journal.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public boolean deleteUser(ObjectId id) {
        userRepository.deleteById(id);
        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
