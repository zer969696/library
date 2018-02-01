package ru.benzoback.library.dao;

import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.util.List;

public interface BookDao {

    List<Book> findAllBooks(int page);
    int deleteBook(int id);
    int addBook(Book book);
    int takeBook(User user, int bookId);
    int putBook(User user, int bookId);

}
