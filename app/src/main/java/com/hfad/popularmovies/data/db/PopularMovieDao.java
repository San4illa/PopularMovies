package com.hfad.popularmovies.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.hfad.popularmovies.data.model.Movie;
import com.hfad.popularmovies.data.model.PopularMovie;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class PopularMovieDao {
    @Query("SELECT * FROM popularmovie")
    public abstract List<Movie> getPopularMovies();

    @Transaction
    public void updateData(List<Movie> movies){
        deleteAll();

        List<PopularMovie> popularMovies = new ArrayList<>();

        for (Movie movie : movies) {
            PopularMovie popularMovie = new PopularMovie(movie.getId(), movie.getVoteAverage(),
                    movie.getTitle(), movie.getPosterPath(), movie.getBackdropPath(),
                    movie.getOverview(), movie.getReleaseDate());

            popularMovies.add(popularMovie);
        }

        insertPopularMovies(popularMovies);
    }

    @Query("DELETE FROM popularmovie")
    public abstract void deleteAll();

    @Insert

    public abstract void insertPopularMovies(List<PopularMovie> movies);
}
