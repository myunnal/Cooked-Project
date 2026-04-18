package com.example.cookinti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FollowDAO {

    @Insert
    void insert(Follow follow);

    @Query("SELECT * from Follow WHERE Follow.fk_fromid = :search")
    List<Follow> getFollowing(long search);

    @Query("SELECT * from Follow WHERE Follow.fk_toid = :search")
    List<Follow> getFollowers(long search);

    @Query("SELECT * from Follow WHERE Follow.fk_fromid = :from AND Follow.fk_toid = :to")
    List<Follow> isFollowing(long from, long to);

    @Query("DELETE from Follow WHERE Follow.fk_fromid = :from AND Follow.fk_toid = :to")
    void removeFollow(long from, long to);
}
