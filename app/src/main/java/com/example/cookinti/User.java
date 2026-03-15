package com.example.cookinti;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "bio")
    private String bio;

    @ColumnInfo(name = "pfp")
    private String pfpLink;


    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void setUsername(@NonNull String user) { this.username = user; }

    public String getUsername() { return this.username; }

    public void setBio(String bio) { this.bio = bio; }

    public String getBio() { return this.bio; }

    public void setPfpLink(String link) { this.pfpLink = link; }

    public String getPfpLink() { return this.pfpLink; }

}
