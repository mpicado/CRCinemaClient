package com.amazonaws.crcinema.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.crcinema.R;
import com.amazonaws.crcinema.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oarriet on 2/23/14.
 */
public class MovieAdapter extends BaseAdapter {

    private List<Movie> movieList = new ArrayList<Movie>();

    public MovieAdapter(List<Movie> movieList){
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.movie_list_card,viewGroup,false);
        }
        Movie movie = movieList.get(i);

        TextView nameTextView = (TextView) view.findViewById(R.id.movieName);
        nameTextView.setText(movie.getName());

        TextView descriptionTextView = (TextView) view.findViewById(R.id.movieDescription);
        String movieDescription = movie.getDescription();
        if (movieDescription.length() > 140){
            descriptionTextView.setText(movie.getDescription().substring(0,140)+"...");
        }else{
            descriptionTextView.setText(movie.getDescription());
        }

        ImageView movieImageView = (ImageView) view.findViewById(R.id.movieImage);
        movieImageView.setTag(movie.getThumbnailUrl());
        Picasso.with(movieImageView.getContext()).load(movie.getThumbnailUrl()).into(movieImageView);

        return view;
    }
}
