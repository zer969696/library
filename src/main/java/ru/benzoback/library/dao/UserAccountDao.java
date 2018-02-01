package ru.benzoback.library.dao;

import ru.benzoback.library.model.UserAccount;

public interface UserAccountDao {

    UserAccount findUserAccountByUserId(int id);
    UserAccount findUserAccountByLogin(String login);
    int addUserAccount(UserAccount userAccount, int userId);
    int deleteUserAccount(int userId);

}
