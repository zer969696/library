package ru.benzoback.library.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper {

    @Autowired
    private RowMapper<User> userRowMapper;

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setAuthor(resultSet.getString("author"));
        book.setTitle(resultSet.getString("title"));
        book.setISN(resultSet.getString("isn"));

        book.setUser(userRowMapper.mapRow(resultSet, i));

        return book;
    }
}
