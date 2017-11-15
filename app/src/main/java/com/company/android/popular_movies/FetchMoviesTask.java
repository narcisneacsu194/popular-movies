package com.company.android.popular_movies;

import android.os.AsyncTask;

import com.company.android.popular_movies.pojo.Movie;
import com.company.android.popular_movies.utils.JsonUtils;
import com.company.android.popular_movies.utils.NetworkUtils;
import java.net.URL;

public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {
    private MainActivity.AsyncTaskCompleteListener<Movie[]> listener;

    public FetchMoviesTask(MainActivity.AsyncTaskCompleteListener listener){
        this.listener = listener;
    }

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
        listener.onTaskComplete(movies);
    }
}
