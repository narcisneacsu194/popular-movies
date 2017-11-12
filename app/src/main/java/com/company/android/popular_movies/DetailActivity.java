package com.company.android.popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.android.popular_movies.utils.ImageUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private String originalTitleString;
    private String posterPathString;
    private String overviewString;
    private Double voteAverageDouble;
    private String releaseDateString;

    private ImageView imageView;
    private TextView originalTitleTextView;
    private TextView overviewTextView;
    private TextView voteAverageTextView;
    private TextView releaseDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = (ImageView) findViewById(R.id.iv_detail_movie_poster);
        originalTitleTextView = (TextView) findViewById(R.id.tv_original_title);
        overviewTextView = (TextView) findViewById(R.id.tv_overview);
        voteAverageTextView = (TextView) findViewById(R.id.tv_vote_average);
        releaseDateTextView = (TextView) findViewById(R.id.tv_release_date);

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
                originalTitleTextView.setText("Title: " + originalTitleString);
                overviewTextView.setText("Description: " + overviewString);
                releaseDateTextView.setText("Release Date: " + releaseDateString);
                voteAverageTextView.setText("Voter Average: " + Double.toString(voteAverageDouble));
            }
        }
    }
}
