package ru.benzoback.library.dao;

import ru.benzoback.library.model.UserAccount;

import java.util.List;

public interface UserAccountDao {

    UserAccount findUserAccountByUserId(int id);
    UserAccount findUserAccountByLogin(String login);
    List<String> findAllLogins();
    int addUserAccount(UserAccount userAccount, int userId);
    int deleteUserAccount(int userId);

}
