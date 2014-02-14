package com.amazonaws.crcinema;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
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
    SharedPreferences app_preferences;
    //server address
    //public static String API_SERVER_ADDRESS = "http://54.213.0.100:8080/CrCinema/";
    //LOCAL IP ADDRESS
    public static String API_SERVER_ADDRESS = "http://192.168.1.6:8080/CRCinema-api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema_list_activity);
        setPreferences();
        loadCinemas();
    }

    private void setPreferences(){
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("apiUrl", API_SERVER_ADDRESS);
        editor.apply();
    }

    private void loadCinemas(){
        dialog = ProgressDialog.show(this,"Loading","Cargando Cinemas");
        String baseApiUrl = app_preferences.getString("apiUrl", API_SERVER_ADDRESS);
        new AsyncProcess().execute(baseApiUrl + "cinema/listCinemas");
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
                        Cinema cinema = new Cinema(obj.getString("name"),obj.getString("address"),obj.getString("cinemaImageName"));
                        listCinemas.add(cinema);
                    }
                    CinemaAdapter cinemaAdapter = new CinemaAdapter(listCinemas,app_preferences.getString("apiUrl", API_SERVER_ADDRESS));
                    ListView cinemaListView = (ListView) findViewById(R.id.cinemaListView);
                    cinemaListView.setAdapter(cinemaAdapter);
                } catch (JSONException e) {
                    //add some logic here to tell the user that the cinemas couldn't be retrieved
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
        }
    }
    
}