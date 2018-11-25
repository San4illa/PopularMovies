package com.hfad.popularmovies.data.network;

import com.hfad.popularmovies.data.model.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("3/movie/popular")
    Call<MovieListResponse> getMovies(@Query("api_key") String apiKey);
}