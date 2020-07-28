package com.thoughtworks.loginexample;

import androidx.room.Room;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class UserDBDataSource {
    private UserDao userDao;

    public UserDBDataSource() {
        String DATABASE_NAME = "app_db";
        AppDatabase appDatabase = Room
                .databaseBuilder(MyApplication.getInstance(), AppDatabase.class, DATABASE_NAME)
                .build();
        userDao = appDatabase.userDao();
    }

    public Maybe<User> findByName(String name) {
        return userDao.findByName(name);
    }

    public Completable save(User user) {
        return userDao.save(user);
    }
}
