package com.example.framework.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.framework.db.dao.FeedDao;
import com.example.framework.db.dao.UserDao;
import com.example.framework.db.model.DBFeed;
import com.example.framework.db.model.DBUser;

@Database(entities = {DBUser.class, DBFeed.class}, version = 1)
public abstract class GobearDatabase extends RoomDatabase {
    public abstract UserDao UserDao();
    public abstract FeedDao FeedDao();
    private static volatile GobearDatabase instance;
    public static GobearDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (GobearDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            GobearDatabase.class, "go_bear_db")
                            .build();
                }
            }
        }
        return instance;
    }
}
