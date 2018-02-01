package ru.benzoback.library.service;

import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.util.List;

public interface UserService {

    User findById(int id);
    List<User> findAllUsers();
    int addUser(User user, UserAccount userAccount);
    int deleteUser(int id);

}
