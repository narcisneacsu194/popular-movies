package com.company.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.company.android.popular_movies.pojo.Movie;
import com.company.android.popular_movies.utils.JsonUtils;
import com.company.android.popular_movies.utils.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movie);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
//        LinearLayoutManager linearLayoutManager =
//                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mRecyclerView.setVisibility(View.VISIBLE);

        new FetchMoviesTask().execute("popularity");
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class<DetailActivity> destinationActivityClass = DetailActivity.class;

        Intent intent = new Intent(context, destinationActivityClass);
        intent.putExtra("originalTitle", movie.getOriginalTitle());
        intent.putExtra("posterPath", movie.getPosterPath());
        intent.putExtra("overview", movie.getOverview());
        intent.putExtra("voteAverage", movie.getVoteAverage());
        intent.putExtra("releaseDate", movie.getReleaseDate());

        startActivity(intent);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]>{
        @Override
        protected Movie[] doInBackground(String... strings) {
            String sortOption = strings[0];
            URL url;
            if(sortOption.equals("popularity")){
                url = NetworkUtils.buildURL("popularity");
            }else{
                url = NetworkUtils.buildURL("rating");
            }
            String jsonResponse;
            Movie[] movies;
            try {
                jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
                movies = JsonUtils.createMovieArrayFromJsonResponse(jsonResponse);
                return movies;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mMovieAdapter.setMovieData(movies);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.action_popularity){
            new FetchMoviesTask().execute("popularity");
            return true;
        }

        if(itemId == R.id.action_rating){
            new FetchMoviesTask().execute("rating");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
