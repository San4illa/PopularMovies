package com.hfad.popularmovies.view.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.hfad.popularmovies.data.DataProvider;
import com.hfad.popularmovies.data.model.Movie;

import java.util.List;

public class MovieListLoader extends AsyncTaskLoader<List<Movie>> {
    private List<Movie> movies;
    private int type;

    MovieListLoader(Context context, int type) {
        super(context);

        this.type = type;
    }

    @Override
    protected void onStartLoading() {
        if (movies != null)
            deliverResult(movies);
        else
            forceLoad();
    }

    @Override
    public void deliverResult(@Nullable List<Movie> data) {
        movies = data;
        super.deliverResult(data);
    }

    @Override
    public List<Movie> loadInBackground() {
        DataProvider dataProvider = new DataProvider(getContext(), type);
        return dataProvider.getMovies();
    }
}
