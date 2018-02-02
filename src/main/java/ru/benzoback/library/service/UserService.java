package ru.benzoback.library.service;

import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.util.List;

public interface UserService {

    User findById(int id);
    User findByLogin(String login);
    List<User> findAllUsers();
    int addUser(User user, UserAccount userAccount);
    int editUser(User user, UserAccount userAccount);
    int deleteUser(int id);

}
