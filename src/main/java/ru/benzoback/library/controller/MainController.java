package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.benzoback.library.dao.BookDao;
import ru.benzoback.library.dao.UserAccountDao;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.Book;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    BookDao dao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserAccountDao userAccountDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test(Model model) {
        List<Book> allBooks = dao.findBooksWithUserName(1);
        model.addAttribute("books", allBooks);
        return "test";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String auth() {
        return "login";
    }
}
