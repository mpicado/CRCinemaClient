package com.amazonaws.crcinema.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amazonaws.crcinema.R;
import com.amazonaws.crcinema.domain.Cinema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpicado on 8/14/13.
 */
public class CinemaAdapter extends BaseAdapter {
    private List<Cinema> cinemaList = new ArrayList<Cinema>();

    public CinemaAdapter(){

    }

    public CinemaAdapter(List<Cinema> list){
        this.cinemaList = list;
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
            view = inflater.inflate(R.layout.cinema_list_detail_activity,viewGroup,false);
        }
        Cinema cinema = cinemaList.get(i);
        TextView nameTextView = (TextView) view.findViewById(R.id.cinemaName);
        nameTextView.setText(cinema.getName());

        TextView addressTextView = (TextView) view.findViewById(R.id.cinemaAdressValue);
        addressTextView.setText(cinema.getAddress());

        ImageButton cinemaImageButton = (ImageButton) view.findViewById(R.id.cinemaImageButton);
        //TODO, need to think of a better way to do this!! this is an ugly patch!!
        if("novacinemas_button_selector".equals(cinema.getButtonSlectorName())){
            cinemaImageButton.setImageResource(R.drawable.novacinemas_button_selector);
        }
        else if("cinemark_button_selector".equals(cinema.getButtonSlectorName())){
            cinemaImageButton.setImageResource(R.drawable.cinemark_button_selector);
        }

        return view;
    }

    public List<Cinema> getCinemaList() {
        return cinemaList;
    }

    public void setCinemaList(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }
}
