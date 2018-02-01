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
public class BookDao {

//    @Autowired
//    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    //private final static RowMapper<User> userMapper = BeanPropertyRowMapper.newInstance(User.class);
    //private final static RowMapper<Book> bookMapper = BeanPropertyRowMapper.newInstance(Book.class);

    @Autowired
    RowMapper<Book> bookRowMapper;
    @Autowired
    RowMapper<User> userRowMapper;

    public List<Book> findBooksWithUserName(Integer page){
        return jdbcTemplate.query("SELECT * FROM books LEFT JOIN users ON books.user_id = users.id;", resultSet -> {
//            List<Book> books = new ArrayList<>();
//            Integer bookId = null;
//            Book currentBook = null;
//            int bookIdx = 0;
//            int itemIdx = 0;
//            while (resultSet.next()) {
//                if (currentBook == null || !bookId.equals(resultSet.getInt("id"))) {
//                    bookId = resultSet.getInt("id");
//                    currentBook = bookRowMapper.mapRow(resultSet, bookIdx++);
//                    itemIdx = 0;
//                    books.add(currentBook);
//                }
//                currentBook.setUser(userRowMapper.mapRow(resultSet, itemIdx++));
//            }
            List<Book> books = new ArrayList<>();
            int id = 0;
            while (resultSet.next()) {
                books.add(bookRowMapper.mapRow(resultSet, id++));
            }

            if (page != null && page > 0 && (books.size() / 5) + 1 >= page) {
                return books.subList((page - 1) * 5, (page * 5) > books.size() ? books.size() : (page * 5));
            }

            if (page != null && (books.size() / 5) + 1 >= page) {
                return null;
            }

            return books;
        });
    }

    public int deleteBook(Integer bookId) {
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?", bookId);
    }

    public int addBook(Book book) {
        return jdbcTemplate.update("INSERT INTO books (isn, author, title, user) VALUES(?, ?, ?, ?)",
                book.getISN(), book.getAuthor(), book.getTitle(), book.getUser());
    }

    public int takeBook(User user, Integer bookId) {
        return jdbcTemplate.update("UPDATE books SET user = ? where id = ?", user, bookId);
    }

    public int putBook(User user, Integer bookId) {
        return jdbcTemplate.update("UPDATE books SET user = ? where id = ?", null, bookId);
    }
}
