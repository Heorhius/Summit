package com.kaptsiug.project.model;

public class User extends General {
    private String name;
    private String login;
    private String password;

    public User(int id, String name, String login, String password) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
    }

    public User(String name, String login, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "name = " + name + ", login = " + login + ", password = " + password;
    }

}
