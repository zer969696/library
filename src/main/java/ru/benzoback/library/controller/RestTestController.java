package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.benzoback.library.dao.BookDao;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.User;

@RestController
@RequestMapping("/api/books")
public class RestTestController {

    private UserDao userDao;
    private BookDao bookDao;

    @Autowired
    public RestTestController(UserDao userDao, BookDao bookDao) {
        this.userDao = userDao;
        this.bookDao = bookDao;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks(@RequestParam(value = "page", required = false) Integer page) {
        return ResponseEntity.ok(bookDao.findAllBooks(page));
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
}
