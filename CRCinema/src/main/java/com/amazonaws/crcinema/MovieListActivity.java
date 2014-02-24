package com.amazonaws.crcinema;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amazonaws.crcinema.adapters.CinemaAdapter;
import com.amazonaws.crcinema.adapters.MovieAdapter;
import com.amazonaws.crcinema.domain.Cinema;
import com.amazonaws.crcinema.domain.Movie;
import com.amazonaws.crcinema.utils.RestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends Activity {

    ProgressDialog dialog;
    String cinemaType;

    //LOCAL IP ADDRESS
    public static String API_SERVER_ADDRESS_MOVIES = "http://192.168.1.3:8080/CRCinema-api/cinema/cinemaData?cinemaType=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);

        Intent intent = getIntent();
        cinemaType = intent.getStringExtra("cinemaType");

        loadMovies();
    }

    private void loadMovies() {
        dialog = ProgressDialog.show(this,"Loading","Cargando Películas");
        new AsyncProcess().execute(API_SERVER_ADDRESS_MOVIES+cinemaType);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_list, menu);
        return true;
    }

    private class AsyncProcess extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try{
                return RestUtil.returnJSON(urls[0]);
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result != null){
                try {
                    List<Movie> listMovies = new ArrayList<Movie>();
                    JSONObject jsonObject = new JSONObject(result);
                    //TODO change this disgusting call
                    JSONArray jArray = (JSONArray)((JSONObject)jsonObject.get("movieGuide")).get("movieDetails");

                    for(int i = 0; i < jArray.length(); i++){
                        JSONObject obj = (JSONObject)jArray.getJSONObject(i).get("movie");
                        //TODO, it would be better to have a json parser for each domain
                        //TODO, the selector needs to be populated in the api
                        Movie movie = new Movie(obj.getString("name"),
                                obj.getString("description"),
                                obj.getString("thumbnailUrl"));
                        listMovies.add(movie);
                    }

                    MovieAdapter cinemaAdapter = new MovieAdapter(listMovies);
                    ListView movieListView = (ListView) findViewById(R.id.movieListView);
                    movieListView.setAdapter(cinemaAdapter);

                } catch (JSONException e) {
                    //TODO add some logic here to tell the user that the cinemas couldn't be retrieved
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
        }
    }

}
