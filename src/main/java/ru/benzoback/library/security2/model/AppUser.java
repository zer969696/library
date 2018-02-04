package ru.benzoback.library.security.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppUser {

    private String login;
    private String password;

    public AppUser(String login, String password) {
        this.login = login;
        this.password = password;
        this.password = new BCryptPasswordEncoder().encode(this.password);
        System.out.println();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
