package ru.benzoback.library.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserAccountRowMapper implements RowMapper<UserAccount> {

    @Autowired
    private RowMapper<User> userRowMapper;

    @Override
    public UserAccount mapRow(ResultSet resultSet, int i) throws SQLException {
        UserAccount userAccount = new UserAccount();

        userAccount.setId(resultSet.getInt("id"));
        userAccount.setLogin(resultSet.getString("login"));
        userAccount.setPassword(resultSet.getString("password"));
        userAccount.setUser(userRowMapper.mapRow(resultSet, i));

        return userAccount;
    }
}
