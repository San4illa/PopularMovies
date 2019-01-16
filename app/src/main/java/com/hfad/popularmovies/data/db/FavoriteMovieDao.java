package com.hfad.popularmovies.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.hfad.popularmovies.data.model.FavoriteMovie;
import com.hfad.popularmovies.data.model.Movie;

import java.util.List;

@Dao
public abstract class FavoriteMovieDao {
    @Query("SELECT * FROM favoritemovie")
    public abstract List<Movie> getFavoriteMovies();

    @Insert
    public abstract void insertFavoriteMovie(FavoriteMovie movie);

    @Delete
    public abstract void deleteFavoriteMovie(FavoriteMovie movie);

    @Query("SELECT * FROM favoritemovie WHERE id = :id")
    public abstract int checkIsFavorite(int id);
}
