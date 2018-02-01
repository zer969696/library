package ru.benzoback.library.dao;

import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.util.List;

public interface UserDao {

    User findById(int id);
    User findByLogin(String login);
    List<User> findAllUsers();
    int addUser(User user, UserAccount userAccount);
    int deleteUser(int id);
}
