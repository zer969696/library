package ru.benzoback.library.model;

import java.util.List;

public class User {

    private Integer id;
    private String login;
    private String password;
    private List<Book> books;

    public User(Integer id, String login, String password, List<Book> books) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
