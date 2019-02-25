package com.example.framework.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.framework.db.model.DBUser;

@Dao
public interface UserDao {
    @Query("SELECT * FROM DBUser where name = :name")
    DBUser findByName(String name);

    @Query("SELECT * FROM DBUser WHERE hash = :hash")
    DBUser findByHash(String hash);

    @Insert
    void insert(DBUser dbUser);

    @Delete
    void delete(DBUser dbUser);
}
