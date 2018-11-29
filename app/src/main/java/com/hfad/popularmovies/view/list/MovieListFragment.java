package com.hfad.popularmovies.view.list;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hfad.popularmovies.R;
import com.hfad.popularmovies.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment {
    private static final int LOADER_ID = 1;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView errorView;

    private MovieAdapter adapter;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }

        adapter = new MovieAdapter(movies);
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.pb);
        errorView = view.findViewById(R.id.error);
        errorView.setOnClickListener(v -> startLoading(true));

        startLoading(false);
    }

    private void startLoading(boolean restart) {
        LoaderManager.LoaderCallbacks<List<Movie>> callbacks = new MovieListFragment.MovieListCallback();

        if (restart) {
            getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, callbacks);
        } else {
            getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, callbacks);
        }

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.GONE);
    }


    private void showMovies(List<Movie> movies) {
        if (movies.size() == 0) {
            showError();
            return;
        }

        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        this.movies.clear();
        this.movies.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    private void showError() {
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    private class MovieListCallback implements LoaderManager.LoaderCallbacks<List<Movie>> {
        @NonNull
        @Override
        public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
            return new MovieListLoader(getContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> movies) {
            showMovies(movies);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {
        }
    }
}
