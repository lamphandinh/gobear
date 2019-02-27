package com.example.framework.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "feed")
public class DBFeed {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "detail")
    String detailUrl;

    @ColumnInfo(name = "thumbnail")
    String thumbnailUrl;

    public DBFeed(@NonNull String title, String description, String detailUrl, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.detailUrl = detailUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
