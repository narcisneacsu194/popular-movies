package com.company.android.popular_movies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String POPULARITY_PATH = "popular";

    private static final String RATING_PATH = "top_rated";

    private static final String API_KEY_QUERY_PARAM = "api_key";

    private static final String API_VALUE = APIKey.getMovieDBApiKey();

    public static URL buildURL(String movieSortType){
        String path;
        if(movieSortType.equals("popularity")){
            path = POPULARITY_PATH;
        }else{
            path = RATING_PATH;
        }

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(path)
                .appendQueryParameter(API_KEY_QUERY_PARAM, API_VALUE).build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        }catch(MalformedURLException murle){
            murle.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasNext = scanner.hasNext();

            if(hasNext){
                return scanner.next();
            }else{
                return null;
            }
        }finally{
            connection.disconnect();
        }
    }
}
