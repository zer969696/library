package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.benzoback.library.dao.BookDao;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

@RestController
@RequestMapping("/api/books")
public class RestBooksController {

    private UserDao userDao;
    private BookDao bookDao;

    @Autowired
    public RestBooksController(UserDao userDao, BookDao bookDao) {
        this.userDao = userDao;
        this.bookDao = bookDao;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "orderBy", required = false) String orderBy,
                                         @RequestParam(value = "sortDir", required = false) String sortDir) {
        return ResponseEntity.ok(bookDao.findAllBooks(page, orderBy, sortDir));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookDao.deleteBook(id));
    }

    @GetMapping("/put/{id}")
    public ResponseEntity<?> putBook(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByLogin(auth.getName());

        return ResponseEntity.ok(bookDao.putBook(user, id));
    }

    @GetMapping("/take/{id}")
    public ResponseEntity<?> takeBook(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByLogin(auth.getName());

        return ResponseEntity.ok(bookDao.takeBook(user, id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestParam(value = "isbn", required = false) String isbn, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "author", required = false) String author) {
        Book book = new Book(isbn, author, title, null);

        return ResponseEntity.ok(bookDao.addBook(book));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editBook(@RequestParam(value = "isbn", required = false) String isbn, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "author", required = false) String author, @RequestParam(value = "id", required = false) Integer id) {
        Book book = new Book(isbn, author, title, null);
        book.setId(id);

        return ResponseEntity.ok(bookDao.updateBook(book));
    }

}
