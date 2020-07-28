package com.thoughtworks.loginexample;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class UserRepository {
    private final UserDBDataSource dataSource;

    public UserRepository(UserDBDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Maybe<User> findByName(String name) {
        return dataSource.findByName(name);
    }

    public Completable save(User user) {
        return dataSource.save(user);
    }
}
