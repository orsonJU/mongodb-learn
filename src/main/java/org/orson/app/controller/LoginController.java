package org.orson.app.controller;

import org.orson.app.dao.UserDao;

public class LoginController {


    private UserDao dao;

    public LoginController(UserDao dao) {
        this.dao = dao;
    }

    public String login(final String username, final String password) {
        if(dao.auth(username,password)) {
            return "login successfully.";
        }else {
            return "authentication failed.";
        }
    }
}
