package org.meral.repository;

import org.meral.entity.UserEntity;
import org.meral.model.User;

import java.util.List;

public interface UserRepository {

    boolean insertUser(UserEntity user);

    List<User> findAllUsers();
}
