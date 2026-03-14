package com.example.cookinti;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
            parentColumns = "id",
            childColumns = "fk_userid",
            onDelete = ForeignKey.CASCADE)
})
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(name = "fk_userid")
    private long fk_userid;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private String imageLink;

    //@NonNull
    @ColumnInfo(name = "description")
    private String description;

    //@NonNull
    @ColumnInfo(name = "ingredients")
    private String ingredients;

    //@NonNull
    @ColumnInfo(name = "steps")
    private String steps;


    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void setFk_userid(@NonNull long id) { this.fk_userid = id; }

    public long getFk_userid() { return this.fk_userid; }

    public void setName(@NonNull String name) { this.name = name; }

    public String getName() { return this.name; }

    public void setImageLink(@NonNull String name) { this.imageLink = name; }

    public String getImageLink() { return this.imageLink; }

    public void setDescription(@NonNull String name) { this.description = name; }

    public String getDescription() { return this.description; }

    public void setIngredients(@NonNull String name) { this.ingredients = name; }

    public String getIngredients() { return this.ingredients; }

    public void setSteps(@NonNull String name) { this.ingredients = steps; }

    public String getSteps() { return this.steps; }

}
