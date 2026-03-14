package com.example.cookinti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

    @Query("DELETE FROM User")
    void deleteAll();

    @Query("SELECT * from User ORDER BY id ASC")
    List<User> getAllUsers();

    @Query("SELECT * from User WHERE User.id = :search")
    User getUser(long search);
}
