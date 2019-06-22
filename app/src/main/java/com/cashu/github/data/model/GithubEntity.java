package com.cashu.github.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repositories")
public class GithubEntity {
    @PrimaryKey
    private int id;

    private String name;

    private boolean _private;

    private String description;

    private int starsCount;

    private int forksCount;

    private int openIssuesCount;

    private int userId;

    @ColumnInfo(name = "user_name")
    private String userName;

    private String ownerAvatarUrl;

    private String userAccountUrl;

    public GithubEntity(int id, String name, boolean _private, String description, int starsCount,
                        int forksCount, int openIssuesCount, int userId, String userName,
                        String ownerAvatarUrl, String userAccountUrl) {
        this.id = id;
        this.name = name;
        this._private = _private;
        this.description = description;
        this.starsCount = starsCount;
        this.forksCount = forksCount;
        this.openIssuesCount = openIssuesCount;
        this.userId = userId;
        this.userName = userName;
        this.ownerAvatarUrl = ownerAvatarUrl;
        this.userAccountUrl = userAccountUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean is_private() {
        return _private;
    }

    public String getDescription() {
        return description;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public int getOpenIssuesCount() {
        return openIssuesCount;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getOwnerAvatarUrl() {
        return ownerAvatarUrl;
    }

    public String getUserAccountUrl() {
        return userAccountUrl;
    }
}
