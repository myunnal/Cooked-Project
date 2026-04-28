package com.example.cookinti;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Recipe.class, Follow.class, Review.class, Favourite.class, FavoritesFolder.class, FolderRecipe.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
    public abstract RecipeDAO recipeDao();
    public abstract FollowDAO followDao();
    public abstract FavouriteDAO favouriteDao();
    public abstract ReviewDAO reviewDao();
    public abstract FavoritesFolderDAO favoritesFolderDAO();
    public abstract FolderRecipeDAO folderRecipeDAO();
}
