package org.meral.service;

import org.meral.entity.UserEntity;
import org.meral.model.User;

import java.util.List;

public interface UserService {

    boolean addUser(UserEntity user);

    List<User> findAll();
}
