package com.amazonaws.crcinema;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.amazonaws.crcinema.adapters.MovieAdapter;
import com.amazonaws.crcinema.domain.Cinema;
import com.amazonaws.crcinema.utils.RestUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
                    Gson gson = new GsonBuilder().create();
                    Cinema cinema = gson.fromJson(result, Cinema.class);

                    MovieAdapter cinemaAdapter = new MovieAdapter(cinema.getMovieGuide());
                    ListView movieListView = (ListView) findViewById(R.id.movieListView);
                    movieListView.setAdapter(cinemaAdapter);

                } catch (Exception e) {
                    //TODO add some logic here to tell the user that the cinemas couldn't be retrieved
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
        }
    }

}
