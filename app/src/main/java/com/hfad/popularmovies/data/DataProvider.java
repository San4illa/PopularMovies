package com.hfad.popularmovies.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hfad.popularmovies.BuildConfig;
import com.hfad.popularmovies.R;
import com.hfad.popularmovies.data.db.MovieDatabase;
import com.hfad.popularmovies.data.model.Movie;
import com.hfad.popularmovies.data.model.MovieListResponse;
import com.hfad.popularmovies.data.network.ApiClient;
import com.hfad.popularmovies.data.network.ApiService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class DataProvider {
    private static final String API_KEY = BuildConfig.API_KEY;

    private Context context;
    private MovieDatabase db;
    private Call<MovieListResponse> call;

    public DataProvider(Context context) {
        this.context = context;

        db = MovieDatabase.getInstance(context);
        call = ApiClient.getClient().create(ApiService.class).getMovies(API_KEY);
    }

    public List<Movie> getData() {
        List<Movie> movies;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            movies = getDataFromInternet();

            if (movies.size() == 0) {
                movies = getDataFromDb();
            } else {
                saveIdDb(movies);
                loadBackdropsToCache(movies);
            }

        } else {
            movies = getDataFromDb();
        }

        return movies;
    }


    private List<Movie> getDataFromInternet() {
        List<Movie> movies = new ArrayList<>();

        try {
            movies.addAll(call.execute().body().getResults());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private void loadBackdropsToCache(List<Movie> movies) {
        for (Movie movie : movies) {
            String url = context.getString(R.string.prefix_high_quality) + movie.getBackdropPath();
            if (!url.equals(""))
                Picasso.get().load(url).fetch();
        }
    }

    private void saveIdDb(List<Movie> movies) {
        for (int i = 0; i < movies.size(); i++)
            movies.get(i).setId(i);

        db.movieDao().deleteAll();
        db.movieDao().insertAllMovies(movies);
    }

    private List<Movie> getDataFromDb() {
        return db.movieDao().getAllMovies();
    }
}
