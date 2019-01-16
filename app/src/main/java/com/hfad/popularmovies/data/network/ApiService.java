package com.hfad.popularmovies.data.network;

import com.hfad.popularmovies.data.model.MovieListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("3/movie/{sorting}")
    Call<MovieListResponse> getMovies(@Path ("sorting") String sortingKey, @Query("api_key") String apiKey);
}