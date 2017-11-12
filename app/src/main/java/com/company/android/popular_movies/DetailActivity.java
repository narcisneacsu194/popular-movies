package com.company.android.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.android.popular_movies.utils.ImageUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String originalTitleString;
        String posterPathString;
        String overviewString;
        Double voteAverageDouble;
        String releaseDateString;

        ImageView imageView;
        TextView originalTitleTextView;
        TextView overviewTextView;
        TextView voteAverageTextView;
        TextView releaseDateTextView;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView =  findViewById(R.id.iv_detail_movie_poster);
        originalTitleTextView = findViewById(R.id.tv_original_title);
        overviewTextView = findViewById(R.id.tv_overview);
        voteAverageTextView = findViewById(R.id.tv_vote_average);
        releaseDateTextView = findViewById(R.id.tv_release_date);

        Intent intent = getIntent();

        if(intent != null){
            if(intent.hasExtra("originalTitle") &&
                    intent.hasExtra("posterPath") &&
                    intent.hasExtra("overview") &&
                    intent.hasExtra("voteAverage") &&
                    intent.hasExtra("releaseDate")){

                originalTitleString = intent.getStringExtra("originalTitle");
                posterPathString = intent.getStringExtra("posterPath");
                overviewString = intent.getStringExtra("overview");
                voteAverageDouble = intent.getDoubleExtra("voteAverage", 0.0);
                releaseDateString = intent.getStringExtra("releaseDate");

                posterPathString = ImageUtils.buildImageUrl(posterPathString);

                Picasso.with(this).load(posterPathString).into(imageView);
                originalTitleTextView.setText(getString(R.string.movie_title, originalTitleString));
                overviewTextView.setText(getString(R.string.movie_description, overviewString));
                releaseDateTextView.setText(getString(R.string.movie_release_date, releaseDateString));
                voteAverageTextView.setText(getString(R.string.movie_voter_average, Double.toString(voteAverageDouble)));
            }
        }
    }
}
