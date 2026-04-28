package com.example.cookinti;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "fk_userid",
                onDelete = ForeignKey.CASCADE)
})
public class FavoritesFolder {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "fk_userid")
    private long fk_userid;

    @ColumnInfo(name = "name")
    private String name;

    public FavoritesFolder() {}

    public FavoritesFolder(long from, String to)
    {
        fk_userid = from;
        name = to;
    }

    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void fk_userid(long id) { this.fk_userid = id; }

    public long fk_userid() { return this.fk_userid; }

    public void name(String folderName) { this.name = folderName; }

    public String name() { return this.name; }
}