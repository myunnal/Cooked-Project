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
        onDelete = ForeignKey.CASCADE),

        @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "fk_recipeid",
        onDelete = ForeignKey.CASCADE)
})
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "fk_userid")
    private long fk_userid;

    @ColumnInfo(name = "fk_recipeid")
    private long fk_recipeid;

    public Favourite() {}

    public Favourite(long from, long to)
    {
        fk_userid = from;
        fk_recipeid = to;
    }

    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void fk_userid(long id) { this.fk_userid = id; }

    public long fk_userid() { return this.fk_userid; }

    public void fk_recipeid(long id) { this.fk_recipeid = id; }

    public long fk_recipeid() { return this.fk_recipeid; }
}
