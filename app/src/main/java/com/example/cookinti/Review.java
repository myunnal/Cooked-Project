package com.example.cookinti;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "fk_user",
        onDelete = ForeignKey.CASCADE),

        @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "fk_recipe",
        onDelete = ForeignKey.CASCADE)
})
public class Review {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "stars")
    private int stars;

    @ColumnInfo(name = "fk_user")
    private long fk_user;

    @ColumnInfo(name = "fk_recipe")
    private long fk_recipe;

    public Review() { }

    public Review(int stars, long fk_user, long fk_recipe){
        this.stars = stars;
        this.fk_user = fk_user;
        this.fk_recipe = fk_recipe;
    }

    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void setStars(int stars) { this.stars = stars; }
    public int getStars() {return stars; }

    public void setFk_user(int fk_user) { this.fk_user = fk_user; }

    public long getFk_user() { return fk_user; }

    public void setFk_recipe(long fk_recipe) { this.fk_recipe = fk_recipe; }

    public long getFk_recipe() { return fk_recipe; }
}
