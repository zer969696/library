package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.benzoback.library.dao.BookDao;
import ru.benzoback.library.model.Book;

import java.util.List;

@RequestMapping("/")
@Controller
public class MainController {

    @Autowired
    BookDao dao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test(Model model) {
        List<Book> allBooks = dao.findBooksWithUserName(1);
        model.addAttribute("books", allBooks);
        return "test";
    }
}
