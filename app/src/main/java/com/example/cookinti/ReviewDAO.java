package com.example.cookinti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReviewDAO {
    @Insert
    void insert(Review review);

    @Query("SELECT * from Review WHERE Review.fk_user = :search")
    List<Review> getReviewsFromUser(long search);

    @Query("SELECT * from Review WHERE Review.fk_recipe = :recipeId")
    List<Review> getRecipeReviews(long recipeId);

    @Query("SELECT AVG(stars) from Review WHERE Review.fk_recipe = :recipeId")
    float getAverageRating(long recipeId);

    @Query("SELECT * from Review WHERE Review.fk_user = :userId AND Review.fk_recipe = :recipeId")
    Review getUserReview(long userId, long recipeId);

    @Query("DELETE from Review WHERE Review.id = :id")
    void removeReview(long id);
}
