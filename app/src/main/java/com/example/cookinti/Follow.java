package com.example.cookinti;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "fk_fromid",
        onDelete = ForeignKey.CASCADE),

        @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "fk_toid",
        onDelete = ForeignKey.CASCADE)
})
public class Follow {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "fk_fromid")
    private long fk_fromid;

    @ColumnInfo(name = "fk_toid")
    private long fk_toid;

    public Follow() {}

    public Follow(long from, long to)
    {
        fk_fromid = from;
        fk_toid = to;
    }

    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void setFk_fromid(long id) { this.fk_fromid = id; }

    public long getFk_fromid() { return this.fk_fromid; }

    public void setFk_toid(long id) { this.fk_toid = id; }

    public long getFk_toid() { return this.fk_toid; }
}
