package com.company.android.popular_movies.utils;

import com.company.android.popular_movies.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {
    public static Movie[] createMovieArrayFromJsonResponse(String jsonResponse) throws JSONException{
        JSONObject object = new JSONObject(jsonResponse);

        JSONArray movieArray = object.getJSONArray("results");

        Movie[] movies = new Movie[movieArray.length()];

        for(int i = 0;i < movieArray.length();i++){
            Movie movie = new Movie();
            JSONObject tempMovieObject = movieArray.getJSONObject(i);
            movie.setOriginalTitle(tempMovieObject.getString("original_title"));
            movie.setPosterPath(tempMovieObject.getString("poster_path"));
            movie.setOverview(tempMovieObject.getString("overview"));
            movie.setVoteAverage(tempMovieObject.getDouble("vote_average"));
            movie.setReleaseDate(tempMovieObject.getString("release_date"));

            movies[i] = movie;
        }

        return movies;
    }
}
