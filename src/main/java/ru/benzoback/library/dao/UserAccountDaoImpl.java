package ru.benzoback.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.benzoback.library.model.UserAccount;

import java.util.ArrayList;
import java.util.List;


@Repository("userAccountDao")
public class UserAccountDaoImpl implements UserAccountDao {

    private RowMapper<UserAccount> userAccountRowMapper;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    UserAccountDaoImpl(RowMapper<UserAccount> userAccountRowMapper, JdbcTemplate jdbcTemplate) {
        this.userAccountRowMapper = userAccountRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserAccount findUserAccountByUserId(int id) {
        return jdbcTemplate.query("SELECT * FROM user_account INNER JOIN users ON user_account.id = users.id WHERE user_account.user_id = ?",
                new Object[]{id},
                resultSet -> {

            if (resultSet.next()) {
                return userAccountRowMapper.mapRow(resultSet, 0);
            }
            return null;
        });
    }

    @Override
    public UserAccount findUserAccountByLogin(String login) {
        return jdbcTemplate.query("SELECT * FROM users, user_account WHERE users.id = user_account.user_id AND login = ?",
                new Object[]{ login },
                resultSet -> {

            if (resultSet.next()) {
                return userAccountRowMapper.mapRow(resultSet, 0);
            }
            return null;
        });
    }

    @Override
    public List<String> findAllLogins() {
        return jdbcTemplate.query("SELECT * FROM users, user_account WHERE users.id = user_account.user_id",
                resultSet -> {

            List<String> logins = new ArrayList<>();

            int i = 0;
            while (resultSet.next()) {
                logins.add(userAccountRowMapper.mapRow(resultSet, i).getLogin());
            }

            return logins;
        });
    }

    @Override
    public int addUserAccount(UserAccount userAccount, int userId) {
        return jdbcTemplate.update("INSERT INTO user_account(login, password, user_id) VALUES ( ?, ?, ? )",
                userAccount.getLogin(),
                userAccount.getPassword(),
                userId
        );
    }

    @Override
    public int deleteUserAccount(int userId) {
        return jdbcTemplate.update("DELETE FROM user_account WHERE user_id = ?", userId);
    }
}
