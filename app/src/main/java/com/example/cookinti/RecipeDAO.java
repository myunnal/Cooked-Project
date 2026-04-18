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

    @Query("SELECT * from Recipe WHERE Recipe.fk_userid = :userid ORDER BY id ASC")
    List<Recipe> getUserRecipes(long userid);

    @Query("SELECT * from Recipe INNER JOIN Follow ON Follow.fk_fromid = :userid" +
            " WHERE Recipe.fk_userid = Follow.fk_toid ORDER BY id ASC")
    List<Recipe> getFollowingRecipes(long userid);

    @Query("SELECT * from Recipe WHERE Recipe.name LIKE :subStr || '%' ORDER BY id ASC")
    List<Recipe> searchRecipes(String subStr);

    @Query("SELECT * from Recipe WHERE Recipe.id = :search")
    Recipe getRecipe(long search);
}
