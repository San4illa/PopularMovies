package com.hfad.popularmovies.view.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hfad.popularmovies.R;
import com.hfad.popularmovies.data.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {
    private static final int LOADER_ID = 2;

    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieRating;
    private TextView movieOverview;
    private ProgressBar progressBar;
    private ImageView moviePoster;
    private ImageView movieBackdrop;

    private int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieId = getActivity().getIntent().getIntExtra("movie", -1);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        movieTitle = view.findViewById(R.id.tv_title);
        movieDate = view.findViewById(R.id.tv_release_date);
        movieRating = view.findViewById(R.id.tv_rating);
        movieOverview = view.findViewById(R.id.tv_overview);
        moviePoster = view.findViewById(R.id.iv_poster);
        movieBackdrop = view.findViewById(R.id.iv_backdrop);

        progressBar = getActivity().findViewById(R.id.pb_detail);
        progressBar.setVisibility(View.VISIBLE);

        LoaderManager.LoaderCallbacks<Movie> callbacks = new MovieDetailFragment.MovieDetailCallBack();
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, callbacks);
    }

    private void finishLoading(Movie movie) {
        movieTitle.setText(movie.getTitle());
        movieDate.setText(movie.getReleaseDate());
        movieRating.setText(movie.getVoteAverage());
        movieOverview.setText(movie.getOverview());

        Picasso.get().load(getString(R.string.prefix_normal_quality) + movie.getPosterPath()).into(moviePoster);
        Picasso.get().load(getString(R.string.prefix_high_quality) + movie.getBackdropPath()).into(movieBackdrop, new Callback() {
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
            return new MovieDetailLoader(getContext(), movieId);
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
