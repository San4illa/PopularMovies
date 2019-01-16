package com.hfad.popularmovies.view.list;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hfad.popularmovies.R;
import com.hfad.popularmovies.data.model.Movie;
import com.hfad.popularmovies.view.detail.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<Movie> movies;

    MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Movie movie = movies.get(position);

        String url = holder.itemView.getContext().getString(R.string.prefix_normal_quality) + movie.getPosterPath();
        if (!url.equals(""))
            Picasso.get().load(url).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    class MovieAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        MovieAdapterViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_item_poster);

            itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                Movie movie = movies.get(getAdapterPosition());
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            });
        }
    }
}
