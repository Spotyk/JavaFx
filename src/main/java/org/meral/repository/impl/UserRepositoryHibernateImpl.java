package org.meral.repository.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.meral.entity.UserEntity;
import org.meral.model.User;
import org.meral.repository.UserRepository;
import org.meral.util.HibernateUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.meral.constant.Constant.LoggerConstants.USER_NOT_FOUND;
import static org.meral.constant.Constant.LoggerConstants.USER_NOT_SAVED;

public class UserRepositoryHibernateImpl implements UserRepository {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    @Override
    public boolean insertUser(UserEntity user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error(USER_NOT_SAVED);
        }
        return false;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            users = session.createQuery("from UserEntity", UserEntity.class).list()
                    .stream().map(this::convertUserEntityIntoUser)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error(USER_NOT_FOUND);
        }
        return users;
    }

    private User convertUserEntityIntoUser(UserEntity userEntity) {
        return new User(userEntity.getName(), userEntity.getSername());
    }
}
