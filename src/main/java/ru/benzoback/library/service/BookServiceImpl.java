package ru.benzoback.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.benzoback.library.dao.BookDao;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao dao;

    @Override
    public List<Book> findAllBooks(int page, String orderBy, String sortDir) {
        return dao.findAllBooks(page, orderBy, sortDir);
    }

    @Override
    public List<Book> findAllBooksById(int id) {
        return dao.findAllBooksById(id);
    }

    @Override
    public int deleteBook(int id) {
        return dao.deleteBook(id);
    }

    @Override
    public int addBook(Book book) {
        return dao.addBook(book);
    }

    @Override
    public int updateBook(Book book) {
        return dao.updateBook(book);
    }

    @Override
    public int updateBookUserId(Book book) {
        return dao.updateBookUserId(book);
    }

    @Override
    public int takeBook(User user, int bookId) {
        return dao.takeBook(user, bookId);
    }

    @Override
    public int putBook(User user, int bookId) {
        return dao.putBook(user, bookId);
    }
}
