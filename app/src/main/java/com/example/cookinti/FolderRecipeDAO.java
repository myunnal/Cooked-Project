package com.example.cookinti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FolderRecipeDAO {

    @Insert
    void insert(FolderRecipe folderRecipe);

    @Query("SELECT * FROM FolderRecipe WHERE FolderRecipe.fk_folderid = :search")
    List<FolderRecipe> getFolderRecipes(long search);

    @Query("SELECT * FROM FolderRecipe WHERE FolderRecipe.fk_folderid = :from AND FolderRecipe.fk_recipeid = :to")
    List<FolderRecipe> isRecipeInFolder(long from, long to);

    @Query("DELETE FROM FolderRecipe WHERE FolderRecipe.fk_folderid = :from AND FolderRecipe.fk_recipeid = :to")
    void removeRecipeFromFolder(long from, long to);

    @Query("SELECT * FROM Recipe INNER JOIN FolderRecipe ON FolderRecipe.fk_folderid = :folderid " +
            "WHERE Recipe.id = FolderRecipe.fk_recipeid ORDER BY Recipe.id ASC")
    List<Recipe> getRecipesInFolder(long folderid);
}