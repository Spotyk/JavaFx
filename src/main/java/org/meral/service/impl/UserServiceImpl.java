package org.meral.service.impl;

import javafx.collections.FXCollections;
import org.meral.entity.UserEntity;
import org.meral.model.User;
import org.meral.repository.UserRepository;
import org.meral.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(UserEntity user) {
        userRepository.insertUser(user);
        return true;
    }

    @Override
    public List<User> findAll() {
        return FXCollections.observableArrayList(userRepository.findAllUsers());
    }
}
