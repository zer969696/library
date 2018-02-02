package ru.benzoback.library.mapper;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.benzoback.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();

        try {
            user.setId(resultSet.getInt("user_id"));
        } catch (JdbcSQLException ex) {
            user.setId(resultSet.getInt("id"));
        }

        user.setName(resultSet.getString("name"));

        if (user.getId() == 0 && user.getName() == null) {
            return null;
        }
        return user;
    }
}
