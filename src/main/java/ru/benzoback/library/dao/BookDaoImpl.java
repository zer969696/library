package ru.benzoback.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository("bookDao")
public class BookDaoImpl implements BookDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RowMapper<Book> bookRowMapper;
    @Autowired
    RowMapper<User> userRowMapper;

    @Override
    public List<Book> findAllBooks(int page){
        return jdbcTemplate.query("SELECT * FROM books LEFT JOIN users ON books.user_id = users.id ORDER BY id ASC", resultSet -> {
            List<Book> books = new ArrayList<>();

            int id = 0;
            while (resultSet.next()) {
                books.add(bookRowMapper.mapRow(resultSet, id++));
            }

            int fromIndex = (page - 1) * 5;
            int toIndex = (page * 5);

            if (page == 0) {
                return books;
            } else if (page * 5 < books.size() && page > 0) {
                return books.subList(fromIndex, toIndex);
            } else if (page > 0 && fromIndex < books.size()) {
                return books.subList(fromIndex, books.size());
            }

            return null;
        });
    }

    @Override
    public int deleteBook(int bookId) {
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?", bookId);
    }

    @Override
    public int addBook(Book book) {
        return jdbcTemplate.update("INSERT INTO books (isn, author, title, user) VALUES(?, ?, ?, ?)",
                book.getISN(), book.getAuthor(), book.getTitle(), book.getUser());
    }

    @Override
    public int takeBook(User user, int bookId) {
        return jdbcTemplate.update("UPDATE books SET user_id = ? where id = ?", user.getId(), bookId);
    }

    @Override
    public int putBook(User user, int bookId) {
        return jdbcTemplate.update("UPDATE books SET user_id = ? where id = ?", null, bookId);
    }
}
