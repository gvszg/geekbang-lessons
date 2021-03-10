package org.geektimes.projects.user.serviceImpl;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.sql.DBConnectionManager;

public class UserServiceImpl implements UserService {
    private final DBConnectionManager dbConnectionManager = new DBConnectionManager();
    private UserRepository userRepository = new DatabaseUserRepository();

    @Override
    public boolean register(User user) {
        System.out.println(user.toString());
        boolean success = userRepository.save(user);
        return success;
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }
}