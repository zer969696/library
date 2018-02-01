package ru.benzoback.library.security.dao;

import ru.benzoback.library.model.UserAccount;
import ru.benzoback.library.security.model.AppUser;
import ru.benzoback.library.service.UserAccountService;

public class AppUserDao {

    private UserAccountService userAccountService;

    public AppUserDao(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public AppUser getAppUser(String login) {
        UserAccount userAccount = userAccountService.findUserAccountByLogin(login);

        if (userAccount != null) {
            return new AppUser(userAccount.getLogin(), userAccount.getPassword());
        } else {
            return null;
        }
    }
}
