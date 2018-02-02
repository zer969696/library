package ru.benzoback.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.benzoback.library.dao.UserAccountDao;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

@RestController
@RequestMapping("/api/users")
public class RestUsersController {

    private UserDao userDao;
    private UserAccountDao userAccountDao;

    @Autowired
    public RestUsersController(UserDao userDao, UserAccountDao userAccountDao) {
        this.userDao = userDao;
        this.userAccountDao = userAccountDao;
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userDao.findAllUsers());
    }

    @GetMapping("/credentials")
    public ResponseEntity<?> findUserCredentialsById(@RequestParam(value = "id", required = false) Integer id) {
        UserAccount userAccount = userAccountDao.findUserAccountByUserId(id);
        return ResponseEntity.ok(userAccount);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userDao.deleteUser(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "login", required = false) String login,
                                     @RequestParam(value = "password", required = false) String password) {
        User user = new User(name);
        UserAccount userAccount = new UserAccount(login, password);

        return ResponseEntity.ok(userDao.addUser(user, userAccount));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editUser(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "login", required = false) String login,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "id", required = false) Integer id) {
        User user = new User(name);
        UserAccount userAccount = new UserAccount(login, password);
        user.setId(id);

        return ResponseEntity.ok(userDao.editUser(user, userAccount));
    }
}
