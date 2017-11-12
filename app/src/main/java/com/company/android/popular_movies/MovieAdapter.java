package com.company.android.popular_movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.company.android.popular_movies.pojo.Movie;
import com.company.android.popular_movies.utils.ImageUtils;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{
    private Movie[] mMovieData;
    private final Context mContext;
    private final MovieAdapterOnClickHandler movieAdapterOnClickHandler;

    public MovieAdapter(Context context, MovieAdapterOnClickHandler movieAdapterOnClickHandler){
        mContext = context;
        this.movieAdapterOnClickHandler = movieAdapterOnClickHandler;
    }

    public interface MovieAdapterOnClickHandler{
        void onClick(Movie movie);
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int movieLayoutId = R.layout.movie_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(movieLayoutId, parent, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        Movie movie = mMovieData[position];
        String stringUrl = ImageUtils.buildImageUrl(movie.getPosterPath());
        Picasso.with(mContext).load(stringUrl).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if(mMovieData == null) return 0;
        return mMovieData.length;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView mImageView;

        public MovieAdapterViewHolder(View view){
            super(view);

            mImageView = view.findViewById(R.id.iv_movie_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Movie movie = mMovieData[pos];

            movieAdapterOnClickHandler.onClick(movie);
        }
    }

    public void setMovieData(Movie[] movieData){
        mMovieData = movieData;
        notifyDataSetChanged();
    }
}
