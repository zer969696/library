package ru.benzoback.library.model;

public class UserAccount {

    private Integer id;
    private String login;
    private String password;
    private User user;

    public UserAccount() {

    }

    public UserAccount(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public UserAccount(String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
