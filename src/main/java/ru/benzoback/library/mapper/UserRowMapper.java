package ru.benzoback.library.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.benzoback.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));

        return user;
    }
}