package com.hfad.popularmovies.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.hfad.popularmovies.data.model.Movie;
import com.hfad.popularmovies.data.model.TopRatedMovie;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class TopRatedMovieDao {
    @Query("SELECT * FROM topratedmovie")
    public abstract List<Movie> getTopRatedMovies();

    @Transaction
    public void updateData(List<Movie> movies){
        deleteAll();

        List<TopRatedMovie> topRatedMovies = new ArrayList<>();

        for (Movie movie : movies) {
            TopRatedMovie topRatedMovie = new TopRatedMovie(movie.getId(), movie.getVoteAverage(),
                    movie.getTitle(), movie.getPosterPath(), movie.getBackdropPath(),
                    movie.getOverview(), movie.getReleaseDate());

            topRatedMovies.add(topRatedMovie);
        }

        insertTopRatedMovies(topRatedMovies);
    }

    @Query("DELETE FROM topratedmovie")
    public abstract void deleteAll();

    @Insert
    public abstract void insertTopRatedMovies(List<TopRatedMovie> movies);
}

