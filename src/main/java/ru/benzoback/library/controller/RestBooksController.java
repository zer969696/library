package ru.benzoback.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;
import ru.benzoback.library.service.BookService;
import ru.benzoback.library.service.UserService;

@RestController
@RequestMapping("/api/books")
public class RestBooksController {

    private BookService bookService;
    private UserService userService;

    public RestBooksController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "orderBy", required = false) String orderBy,
                                         @RequestParam(value = "sortDir", required = false) String sortDir) {
        return ResponseEntity.ok(bookService.findAllBooks(page, orderBy, sortDir));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/put/{id}")
    public ResponseEntity<?> putBook(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByLogin(auth.getName());

        return ResponseEntity.ok(bookService.putBook(user, id));
    }

    @GetMapping("/take/{id}")
    public ResponseEntity<?> takeBook(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByLogin(auth.getName());

        return ResponseEntity.ok(bookService.takeBook(user, id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestParam(value = "isbn", required = false) String isbn, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "author", required = false) String author) {
        Book book = new Book(isbn, author, title, null);

        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editBook(@RequestParam(value = "isbn", required = false) String isbn, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "author", required = false) String author, @RequestParam(value = "id", required = false) Integer id) {
        Book book = new Book(isbn, author, title, null);
        book.setId(id);

        return ResponseEntity.ok(bookService.updateBook(book));
    }

}
