package com.company.android.popular_movies.utils;

public class ImageUtils {
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185";

    public static String buildImageUrl(String posterPath){
        return BASE_IMAGE_URL + posterPath;
    }
}
