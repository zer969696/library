package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.benzoback.library.dao.BookDao;

@RestController
@RequestMapping("/api/books")
public class RestTestController {

    @Autowired
    BookDao bookDao;

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks(@RequestParam(value = "page", required = false) Integer page) {
        return ResponseEntity.ok(bookDao.findBooksWithUserName(page));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookDao.deleteBook(id));
    }
}
