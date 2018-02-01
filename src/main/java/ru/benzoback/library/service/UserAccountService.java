package ru.benzoback.library.service;

import ru.benzoback.library.model.UserAccount;

public interface UserAccountService {

    UserAccount findUserAccountByUserId(int id);
    UserAccount findUserAccountByLogin(String login);
    int addUserAccount(UserAccount userAccount, int userId);
    int deleteUserAccount(int userId);

}
