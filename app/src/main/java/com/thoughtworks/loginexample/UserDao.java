package com.thoughtworks.loginexample;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface UserDao {
    @Insert
    Completable save(User user);

    @Query("SELECT * FROM user WHERE name = :name")
    Maybe<User> findByName(String name);
}
