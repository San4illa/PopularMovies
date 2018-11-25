package com.hfad.popularmovies.view.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hfad.popularmovies.R;
import com.hfad.popularmovies.data.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private static final int LOADER_ID = 2;

    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieRating;
    private TextView movieOverview;
    private ProgressBar progressBar;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initUI();

        movieId = getIntent().getIntExtra("movie", -1);

        LoaderManager.LoaderCallbacks<Movie> callbacks = new MovieDetailCallBack();
        getSupportLoaderManager().initLoader(LOADER_ID, null, callbacks);
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        movieTitle = findViewById(R.id.tv_title);
        movieDate = findViewById(R.id.tv_release_date);
        movieRating = findViewById(R.id.tv_rating);
        movieOverview = findViewById(R.id.tv_overview);
        progressBar = findViewById(R.id.pb_detail);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void finishLoading(Movie movie) {
        movieTitle.setText(movie.getTitle());
        movieDate.setText(movie.getReleaseDate());
        movieRating.setText(movie.getVoteAverage());
        movieOverview.setText(movie.getOverview());

        Picasso.get().load(getString(R.string.prefix_normal_quality) + movie.getPosterPath()).into((ImageView) findViewById(R.id.iv_poster));
        Picasso.get().load(getString(R.string.prefix_high_quality) + movie.getBackdropPath()).into((ImageView) findViewById(R.id.iv_backdrop), new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    private class MovieDetailCallBack implements LoaderManager.LoaderCallbacks<Movie> {
        @NonNull
        @Override
        public Loader<Movie> onCreateLoader(int id, @Nullable Bundle args) {
            return new MovieDetailLoader(MovieDetailActivity.this, movieId);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Movie> loader, Movie movie) {
            finishLoading(movie);
        }

        @Override
        public void onLoaderReset(@NonNull Loader loader) {
        }
    }
}
