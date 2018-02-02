package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.benzoback.library.dao.BookDao;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.util.List;

@Controller
public class MainController {

    private BookDao bookDao;
    private UserDao userDao;

    @Autowired
    public MainController(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test(Model model) {
        List<Book> allBooks = bookDao.findAllBooks(1, "", "");
        model.addAttribute("books", allBooks);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userDao.findByLogin(auth.getName());
        model.addAttribute("currentUser", user.getName());

        return "main";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {
        List<User> users = userDao.findAllUsers();
        model.addAttribute("users", users);

        return "users";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String auth() {
        return "login";
    }
}
