package com.example.cookinti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDAO {

    @Insert
    void insert(Favourite favourite);

    @Query("SELECT * from Favourite WHERE Favourite.fk_userid = :search")
    List<Favourite> getUserFavourites(long search);

    @Query("SELECT * from Favourite WHERE Favourite.fk_userid = :from AND Favourite.fk_recipeid = :to")
    List<Favourite> isFavourite(long from, long to);

    @Query("DELETE from Favourite WHERE Favourite.fk_userid = :from AND Favourite.fk_recipeid = :to")
    void removeFavourite(long from, long to);
}
