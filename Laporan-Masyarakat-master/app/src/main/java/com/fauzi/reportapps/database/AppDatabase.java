package com.fauzi.reportapps.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.fauzi.reportapps.dao.DatabaseDao;
import com.fauzi.reportapps.model.ModelDatabase;



@Database(entities = {ModelDatabase.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DatabaseDao databaseDao();
}
