package com.company.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mRecyclerView.setVisibility(View.VISIBLE);

        apiRequest("popularity");
    }

    private void apiRequest(String criteria) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnectivity = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnectivity)
        new FetchMoviesTask(new FetchMyDataCompleteListener()).execute(criteria);
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

    public interface AsyncTaskCompleteListener<T>{
        public void onTaskComplete(T result);
    }

    public class FetchMyDataCompleteListener implements AsyncTaskCompleteListener<Movie[]>{
        @Override
        public void onTaskComplete(Movie[] movies) {
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
            apiRequest("popularity");
            return true;
        }

        if(itemId == R.id.action_rating){
            apiRequest("rating");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
