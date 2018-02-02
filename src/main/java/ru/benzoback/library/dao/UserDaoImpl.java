package ru.benzoback.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private RowMapper<User> userRowMapper;
    private UserAccountDao userAccountDao;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(RowMapper<User> userRowMapper, UserAccountDao userDao, JdbcTemplate jdbcTemplate) {
        this.userRowMapper = userRowMapper;
        this.userAccountDao = userDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{ id }, resultSet -> {
            if (resultSet.next()) {
                return userRowMapper.mapRow(resultSet, 0);
            } else {
                return null;
            }
        });
    }

    @Override
    public User findByLogin(String login) {
        return jdbcTemplate.query("SELECT * FROM users, user_account WHERE users.id = user_account.id AND user_account.login = ?", new Object[]{ login }, resultSet -> {
            if (resultSet.next()) {
                return userRowMapper.mapRow(resultSet, 0);
            } else {
                return null;
            }
        });
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", resultSet -> {
            List<User> usersList = new ArrayList<>();

            int id = 0;
            while (resultSet.next()) {
                usersList.add(userRowMapper.mapRow(resultSet, id));
            }

            return usersList;
        });
    }

    @Override
    public int addUser(User user, UserAccount userAccount) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            return preparedStatement;
        }, holder);
        return userAccountDao.addUserAccount(userAccount, holder.getKey().intValue());
    }

    @Override
    public int editUser(User user, UserAccount userAccount) {
        jdbcTemplate.update("UPDATE user_account SET login = ?, password = ? WHERE user_id = ?", userAccount.getLogin(), userAccount.getPassword(), user.getId());
        return jdbcTemplate.update("UPDATE users SET name = ? WHERE id = ?", user.getName(), user.getId());

    }

    @Override
    public int deleteUser(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE users.id = ?", id);
    }
}
