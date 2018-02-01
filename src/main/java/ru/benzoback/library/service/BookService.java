package ru.benzoback.library.service;

import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks(int page);
    int deleteBook(int id);
    int addBook(Book book);
    int takeBook(User user, int bookId);
    int putBook(User user, int bookId);

}
