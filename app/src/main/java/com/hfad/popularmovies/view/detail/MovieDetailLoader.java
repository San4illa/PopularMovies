package com.hfad.popularmovies.view.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.hfad.popularmovies.data.db.MovieDatabase;
import com.hfad.popularmovies.data.model.Movie;

public class MovieDetailLoader extends AsyncTaskLoader<Movie> {
    private int id;
    private Movie movie;

    MovieDetailLoader(@NonNull Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        if (movie != null)
            deliverResult(movie);
        else
            forceLoad();
    }

    @Override
    public void deliverResult(@Nullable Movie data) {
        movie = data;
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public Movie loadInBackground() {
        MovieDatabase db = MovieDatabase.getInstance(getContext());
        return db.movieDao().getMovie(id);
    }
}
