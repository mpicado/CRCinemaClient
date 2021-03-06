package com.amazonaws.crcinema.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.crcinema.R;
import com.amazonaws.crcinema.domain.Cinema;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpicado on 8/14/13.
 */
public class CinemaAdapter extends BaseAdapter {
    private List<Cinema> cinemaList = new ArrayList<Cinema>();
    String apiUrl;

    public CinemaAdapter(List<Cinema> list, String apiUrl){
        this.cinemaList = list;
        this.apiUrl = apiUrl;
    }

    @Override
    public int getCount() {
        return cinemaList.size();
    }

    @Override
    public Object getItem(int i) {
        return cinemaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.cinema_list_card,viewGroup,false);
        }
        Cinema cinema = cinemaList.get(i);

        view.setTag(cinema.getCinemaType());

        TextView nameTextView = (TextView) view.findViewById(R.id.cinemaName);
        nameTextView.setText(cinema.getName());

        TextView addressTextView = (TextView) view.findViewById(R.id.cinemaAddress);
        addressTextView.setText(cinema.getAddress());

        ImageView cinemaImageView = (ImageView) view.findViewById(R.id.cinemaImage);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)view.getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        String URL = "";
        switch(metrics.densityDpi){
            case DisplayMetrics.DENSITY_MEDIUM:
                URL = this.apiUrl + "/images/drawable-mdpi/" + cinema.getCinemaImageName();
                break;
            case DisplayMetrics.DENSITY_HIGH:
                URL = this.apiUrl + "/images/drawable-hdpi/" + cinema.getCinemaImageName();
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                URL = this.apiUrl + "/images/drawable-xhdpi/" + cinema.getCinemaImageName();
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                URL = this.apiUrl + "/images/drawable-xxhdpi/" + cinema.getCinemaImageName();
                break;
        }

        cinemaImageView.setTag(URL);
        Picasso.with(cinemaImageView.getContext()).load(URL).into(cinemaImageView);

        return view;
    }

    public List<Cinema> getCinemaList() {
        return cinemaList;
    }

    public void setCinemaList(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }
}
