package com.hfad.popularmovies.view.list;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hfad.popularmovies.R;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MovieListFragment fragment = new MovieListFragment();

        fragmentTransaction.replace(R.id.fragment_list_container, fragment);
        fragmentTransaction.commit();
    }
}