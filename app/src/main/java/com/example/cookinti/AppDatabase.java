package com.example.cookinti;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Recipe.class, Follow.class, Favourite.class, Review.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
    public abstract RecipeDAO recipeDao();
    public abstract FollowDAO followDao();
    public abstract FavouriteDAO favouriteDao();
    public abstract ReviewDAO reviewDao();
}
