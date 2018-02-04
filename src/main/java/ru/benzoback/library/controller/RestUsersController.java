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
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "login", required = false) String login,
                                     @RequestParam(value = "password", required = false) String password) {
        User user = new User(name);
        UserAccount userAccount = new UserAccount(login, password);

        if (userService.addUser(user, userAccount) == 1) {
            if (!inMemoryUserDetailsManager.userExists(login)) {
                inMemoryUserDetailsManager.createUser(
                        new org.springframework.security.core.userdetails.User(
                                login,
                                password,
                                true,
                                true,
                                true,
                                true,
                                new ArrayList<>())
                );
            }
        }

        return ResponseEntity.ok(1);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editUser(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "login", required = false) String login,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "id", required = false) Integer id) {
        User user = new User(name);
        UserAccount userAccount = new UserAccount(login, password);
        user.setId(id);

        return ResponseEntity.ok(userService.editUser(user, userAccount));
    }
}
