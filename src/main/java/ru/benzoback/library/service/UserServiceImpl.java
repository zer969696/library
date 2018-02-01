package ru.benzoback.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao dao;

    @Override
    public User findById(int id) {
        return dao.findById(id);
    }

    @Override
    public User findByLogin(String login) {
        return dao.findByLogin(login);
    }

    @Override
    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    @Override
    public int addUser(User user, UserAccount userAccount) {
        return dao.addUser(user, userAccount);
    }

    @Override
    public int deleteUser(int id) {
        return dao.deleteUser(id);
    }
}
