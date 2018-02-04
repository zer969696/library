package ru.benzoback.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;
import ru.benzoback.library.service.UserAccountService;
import ru.benzoback.library.service.UserService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/users")
public class RestUsersController {

    private UserService userService;
    private UserAccountService userAccountService;
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public RestUsersController(UserService userService, UserAccountService userAccountService, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userService = userService;
        this.userAccountService = userAccountService;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @PostMapping("/credentials")
    public ResponseEntity<?> findUserCredentialsById(@RequestParam(value = "id", required = false) Integer id) {
        return ResponseEntity.ok(userAccountService.findUserAccountByUserId(id));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {

        UserAccount userAccount = userAccountService.findUserAccountByUserId(id);

        if (userService.deleteUser(id) == 1) {
            inMemoryUserDetailsManager.deleteUser(userAccount.getLogin());
            return ResponseEntity.ok(1);
        }

        return ResponseEntity.ok(0);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "login", required = false) String login,
                                     @RequestParam(value = "password", required = false) String password) {

        User user = new User(name);
        UserAccount userAccount = new UserAccount(login, password);

        if (userService.addUser(user, userAccount) == 1) {
            inMemoryUserDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
                    login, password, new ArrayList<>()
            ));

            return ResponseEntity.ok(1);
        }

        return ResponseEntity.ok(0);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editUser(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "login", required = false) String login,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "id", required = false) Integer id) {
        User user = new User(name);
        UserAccount userAccount = new UserAccount(login, password);
        user.setId(id);

        String oldLogin = userAccountService.findUserAccountByUserId(id).getLogin();

        if (userService.editUser(user, userAccount) == 1) {
            inMemoryUserDetailsManager.createUser(new org.springframework.security.core.userdetails.User(
                    login, password, new ArrayList<>()
            ));

            inMemoryUserDetailsManager.deleteUser(oldLogin);

            return ResponseEntity.ok(1);
        }

        return ResponseEntity.ok(0);
    }
}
