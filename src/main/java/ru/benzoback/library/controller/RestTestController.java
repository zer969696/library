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
    public ResponseEntity<?> getPersons() {
        return ResponseEntity.ok(bookDao.findBooksWithUserName());
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookDao.deleteBook(id));
    }
}
