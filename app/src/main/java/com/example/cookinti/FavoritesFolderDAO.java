package com.example.cookinti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoritesFolderDAO {

    @Insert
    void insert(FavoritesFolder folder);

    @Query("SELECT * FROM FavoritesFolder WHERE fk_userid = :userId ORDER BY id DESC")
    List<FavoritesFolder> getFoldersForUser(long userId);

    @Query("SELECT * FROM FavoritesFolder WHERE id = :folderId")
    FavoritesFolder getFolderById(long folderId);

    @Query("DELETE FROM FavoritesFolder WHERE id = :folderId")
    void deleteFolder(long folderId);
}