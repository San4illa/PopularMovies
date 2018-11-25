package com.hfad.popularmovies.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.hfad.popularmovies.data.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies.db";
    private static MovieDatabase instance;

    public abstract MovieDao movieDao();

    public static MovieDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class,
                        DATABASE_NAME)
                        .build();
            }
        }
        return instance;
    }
}
