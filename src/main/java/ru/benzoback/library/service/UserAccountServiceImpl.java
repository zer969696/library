package ru.benzoback.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.benzoback.library.dao.UserAccountDao;
import ru.benzoback.library.model.UserAccount;

import java.util.List;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    UserAccountDao dao;

    @Override
    public UserAccount findUserAccountByUserId(int id) {
        return dao.findUserAccountByUserId(id);
    }

    @Override
    public UserAccount findUserAccountByLogin(String login) {
        return dao.findUserAccountByLogin(login);
    }

    @Override
    public List<String> findAllLogins() {
        return dao.findAllLogins();
    }

    @Override
    public int addUserAccount(UserAccount userAccount, int userId) {
        return dao.addUserAccount(userAccount, userId);
    }

    @Override
    public int deleteUserAccount(int userId) {
        return dao.deleteUserAccount(userId);
    }
}
