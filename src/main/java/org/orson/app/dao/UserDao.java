package org.orson.app.dao;

public interface UserDao {

    Boolean auth(final String username, final String password);

    Boolean register(final String username, final String password);
}
