package com.amazonaws.crcinema;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

import com.amazonaws.crcinema.adapters.CinemaAdapter;
import com.amazonaws.crcinema.domain.Cinema;
import com.amazonaws.crcinema.utils.RestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CinemaListActivity extends Activity {

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_list_activity);
        loadCinemas();
    }

    private void loadCinemas(){
        dialog = ProgressDialog.show(this,"Loading","Cargando Cinemas");
        new AsyncProcess().execute("http://54.213.0.100:8080/CrCinema/cinema/listCinemas");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cinema_list, menu);
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
                    List<Cinema> listCinemas = new ArrayList<Cinema>();
                    JSONArray jArray = new JSONArray(result);

                    for(int i = 0; i < jArray.length(); i++){
                        JSONObject obj = jArray.getJSONObject(i);
                        //TODO, it would be better to have a json parser for each domain
                        //TODO, the selector needs to be populated in the api
                        Cinema cinema = new Cinema(obj.getString("name"),obj.getString("address"),null);
                        listCinemas.add(cinema);
                    }
                    CinemaAdapter cinemaAdapter = new CinemaAdapter(listCinemas);
                    ListView cinemaListView = (ListView) findViewById(R.id.cinemaListView);
                    cinemaListView.setAdapter(cinemaAdapter);
                } catch (JSONException e) {
                    //add some logic here to tell the user that the cinemas couldnt be retrieved
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
        }
    }
    
}
