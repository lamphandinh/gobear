package com.example.framework.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.framework.db.model.DBFeed;

@Dao
public interface FeedDao {

    @Query("SELECT * FROM feed")
    public DBFeed[] loadALlFeeds();

    @Insert
    public void insertFeeds(DBFeed... feeds);

    @Query("DELETE FROM feed")
    public void deleteFeeds();
}
