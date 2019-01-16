package com.hfad.popularmovies.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hfad.popularmovies.data.db.MovieDatabase;
import com.hfad.popularmovies.data.model.Movie;
import com.hfad.popularmovies.data.model.MovieListResponse;
import com.hfad.popularmovies.data.network.ApiClient;
import com.hfad.popularmovies.data.network.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import static com.hfad.popularmovies.BuildConfig.API_KEY;

public class DataProvider {
    private Context context;
    private MovieDatabase db;
    private Call<MovieListResponse> call;

    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String FAVORITES = "favorites";

    private static final String[] FRAGMENT_TYPES = {
            POPULAR,
            TOP_RATED,
            FAVORITES
    };

    private int type;

    public DataProvider(Context context, int type) {
        this.context = context;

        this.type = type;

        db = MovieDatabase.getInstance(context);
        call = ApiClient.getClient().create(ApiService.class).getMovies(FRAGMENT_TYPES[type], API_KEY);
    }

    public List<Movie> getMovies() {
        List<Movie> movies;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && type != 2) {
            movies = getMoviesFromInternet();

            if (movies.size() == 0) {
                movies = getDataFromDb();
            } else {
                saveIdDb(movies);
            }

        } else {
            movies = getDataFromDb();
        }

        return movies;
    }


    private List<Movie> getMoviesFromInternet() {
        List<Movie> movies = new ArrayList<>();

        try {
            movies.addAll(call.execute().body().getResults());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    private void saveIdDb(List<Movie> movies) {
        switch (type) {
            case 0:
                db.popularMovieDao().updateData(movies);
                break;
            case 1:
                db.topRatedMovieDao().updateData(movies);
                break;
        }
    }

    private List<Movie> getDataFromDb() {
        switch (type) {
            case 0:
                return db.popularMovieDao().getPopularMovies();
            case 1:
                return db.topRatedMovieDao().getTopRatedMovies();
            case 2:
                return db.favoriteMovieDao().getFavoriteMovies();
            default:
                return null;
        }
    }
}