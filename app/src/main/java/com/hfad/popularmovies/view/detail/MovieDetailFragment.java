package com.hfad.popularmovies.view.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hfad.popularmovies.R;
import com.hfad.popularmovies.data.db.MovieDatabase;
import com.hfad.popularmovies.data.model.FavoriteMovie;
import com.hfad.popularmovies.data.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetailFragment extends Fragment {
    private TextView movieTitle;
    private TextView movieDate;
    private TextView movieRating;
    private TextView movieOverview;
    private ProgressBar progressBar;
    private ImageView moviePoster;
    private ImageView movieBackdrop;
    private FloatingActionButton fab;

    private boolean isFavorite = false;

    private MovieDatabase db = MovieDatabase.getInstance(getActivity());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        Movie movie = getActivity().getIntent().getParcelableExtra("movie");
        new Thread(() -> {
            int a = db.favoriteMovieDao().checkIsFavorite(movie.getId());
            if (a > 0) {
                isFavorite = true;
                fab.setImageResource(R.drawable.ic_action_favorite);
            }
        }).start();

        movieTitle = view.findViewById(R.id.tv_title);
        movieDate = view.findViewById(R.id.tv_release_date);
        movieRating = view.findViewById(R.id.tv_rating);
        movieOverview = view.findViewById(R.id.tv_overview);
        moviePoster = view.findViewById(R.id.iv_poster);
        movieBackdrop = view.findViewById(R.id.iv_backdrop);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            if (isFavorite) {
                isFavorite = false;
                fab.setImageResource(R.drawable.ic_action_favorite_border);
                new Thread(() -> db.favoriteMovieDao().deleteFavoriteMovie(convertToFavoriteMovie(movie))).start();
            } else {
                isFavorite = true;
                fab.setImageResource(R.drawable.ic_action_favorite);
                new Thread(() -> db.favoriteMovieDao().insertFavoriteMovie(convertToFavoriteMovie(movie))).start();

            }
        });

        progressBar = getActivity().findViewById(R.id.pb_detail);
        progressBar.setVisibility(View.VISIBLE);

        finishLoading(movie);
    }

    private FavoriteMovie convertToFavoriteMovie(Movie movie) {
        return new FavoriteMovie(movie.getId(), movie.getVoteAverage(),
                movie.getTitle(), movie.getPosterPath(),
                movie.getBackdropPath(), movie.getOverview(),
                movie.getReleaseDate());
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
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
