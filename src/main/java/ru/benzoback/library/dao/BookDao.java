package ru.benzoback.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookDao {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private final static RowMapper<User> userMapper = BeanPropertyRowMapper.newInstance(User.class);
    private final static RowMapper<Book> bookMapper = BeanPropertyRowMapper.newInstance(Book.class);

    public List<Book> findBooksWithUserName(){
        return jdbcTemplate.query("SELECT * FROM books LEFT JOIN users ON books.user_id = users.id;", resultSet -> {
            List<Book> books = new ArrayList<>();
            Integer bookId = null;
            Book currentBook = null;
            int bookIdx = 0;
            int itemIdx = 0;
            while (resultSet.next()) {
                if (currentBook == null || !bookId.equals(resultSet.getInt("id"))) {
                    bookId = resultSet.getInt("id");
                    currentBook = bookMapper.mapRow(resultSet, bookIdx++);
                    itemIdx = 0;
                    books.add(currentBook);
                }
                currentBook.setUser(userMapper.mapRow(resultSet, itemIdx++));
            }
            return books;
        });
    }
}
