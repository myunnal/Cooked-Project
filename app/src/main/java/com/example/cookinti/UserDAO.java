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

    @Query("SELECT * from User INNER JOIN Follow ON Follow.fk_fromid = :userid" +
            " WHERE Follow.fk_toid = User.id ORDER BY id ASC")
    List<User> getFollowedUsers(long userid);

    @Query("SELECT * from User WHERE User.id = :search")
    User getUser(long search);

    @Query("SELECT * from User WHERE User.username = :search")
    User findUsername(String search);

    @Query("UPDATE User SET pfp = :newImage WHERE id = :userid")
    void updateImage(long userid, String newImage);

    @Query("UPDATE User SET bio = :newBio WHERE id = :userid")
    void updateBio(long userid, String newBio);

    @Query("UPDATE User SET pronouns = :newPronouns WHERE id = :userid")
    void updatePronouns(long userid, String newPronouns);
}
