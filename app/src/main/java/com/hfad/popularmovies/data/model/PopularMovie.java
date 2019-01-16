package com.hfad.popularmovies.data.model;

import android.arch.persistence.room.Entity;

@Entity
public class PopularMovie extends Movie {
    public PopularMovie(int id, String voteAverage, String title, String posterPath, String backdropPath, String overview, String releaseDate) {
        super(id, voteAverage, title, posterPath, backdropPath, overview, releaseDate);
    }
}
