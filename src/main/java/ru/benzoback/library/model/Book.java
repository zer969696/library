package ru.benzoback.library.model;

public class Book {

    private Integer id;
    private String ISN;
    private String author;
    private String title;
    private User user;

    public Book() {

    }

    public Book(Integer id, String isn, String author, String title, User user) {
        this.id = id;
        this.ISN = isn;
        this.author = author;
        this.title = title;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getISN() {
        return ISN;
    }

    public void setISN(String ISN) {
        this.ISN = ISN;
    }
}
