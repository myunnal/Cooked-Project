package com.example.cookinti;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = FavoritesFolder.class,
                parentColumns = "id",
                childColumns = "fk_folderid",
                onDelete = ForeignKey.CASCADE),

        @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "fk_recipeid",
                onDelete = ForeignKey.CASCADE)
})
public class FolderRecipe {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "fk_folderid")
    private long fk_folderid;

    @ColumnInfo(name = "fk_recipeid")
    private long fk_recipeid;

    public FolderRecipe() {}

    public FolderRecipe(long from, long to)
    {
        fk_folderid = from;
        fk_recipeid = to;
    }

    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void fk_folderid(long id) { this.fk_folderid = id; }

    public long fk_folderid() { return this.fk_folderid; }

    public void fk_recipeid(long id) { this.fk_recipeid = id; }

    public long fk_recipeid() { return this.fk_recipeid; }
}