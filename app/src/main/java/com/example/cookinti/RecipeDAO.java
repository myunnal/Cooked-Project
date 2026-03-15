package com.example.cookinti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void insert(Recipe recipe);

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query("SELECT * from Recipe ORDER BY id ASC")
    List<Recipe> getAllRecipes();

    @Query("SELECT * from Recipe WHERE Recipe.id = :search")
    Recipe getRecipe(long search);
}
