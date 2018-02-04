package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;
import ru.benzoback.library.service.BookService;
import ru.benzoback.library.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    private BookService bookService;
    private UserService userService;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    public MainController(BookService bookService,
                          UserService userService,
                          AuthenticationManagerBuilder authenticationManagerBuilder) {

        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.bookService = bookService;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(HttpServletResponse response, Model model) throws IOException {

        List<Book> allBooks = bookService.findAllBooks(1, "", "");
        model.addAttribute("books", allBooks);

        SecurityContext context = SecurityContextHolder.getContext();
        User user = userService.findByLogin(context.getAuthentication().getName());

        try {
            model.addAttribute("currentUser", user.getName());
        } catch (NullPointerException ex) {
            response.sendError(401, "User account was deleted!");
        }


        return "main";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model) {

        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);

        return "users";
    }
}
