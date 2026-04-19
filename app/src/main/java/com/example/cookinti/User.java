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

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    @NonNull
    @ColumnInfo(name = "pronouns")
    private String pronouns;

    @ColumnInfo(name = "bio")
    private String bio;

    @ColumnInfo(name = "pfp")
    private String pfpLink;

    public User() {
    }

    public User(@NonNull String username, @NonNull String password, @NonNull String pronouns,
                String bio, String pfpLink) {
        this.username = username;
        this.password = password;
        this.pronouns = pronouns;
        this.bio = bio;
        this.pfpLink = pfpLink;
    }

    public void setId(@NonNull long id) { this.id = id; }

    public long getId() { return this.id; }

    public void setUsername(@NonNull String user) { this.username = user; }

    public String getUsername() { return this.username; }

    public void setPassword(@NonNull String pass) { this.password = pass; }

    public String getPassword() { return this.password; }

    public void setPronouns(@NonNull String pronouns) { this.pronouns = pronouns; }

    public String getPronouns() { return this.pronouns; }

    public void setBio(String bio) { this.bio = bio; }

    public String getBio() { return this.bio; }

    public void setPfpLink(String link) { this.pfpLink = link; }

    public String getPfpLink() { return this.pfpLink; }

}
