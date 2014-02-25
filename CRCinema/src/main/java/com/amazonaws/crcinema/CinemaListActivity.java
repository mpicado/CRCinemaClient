package com.amazonaws.crcinema;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amazonaws.crcinema.adapters.CinemaAdapter;
import com.amazonaws.crcinema.domain.Cinema;
import com.amazonaws.crcinema.utils.RestUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CinemaListActivity extends Activity {

    ProgressDialog dialog;
    SharedPreferences app_preferences;

    //LOCAL IP ADDRESS
    public static String API_SERVER_ADDRESS = "http://192.168.1.3:8080/CRCinema-api/";

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
                    List<Cinema> listCinemas;

                    Gson gson = new GsonBuilder().create();
                    listCinemas = gson.fromJson(result, new TypeToken<List<Cinema>>(){}.getType());

                    CinemaAdapter cinemaAdapter = new CinemaAdapter(listCinemas,app_preferences.getString("apiUrl", API_SERVER_ADDRESS));
                    ListView cinemaListView = (ListView) findViewById(R.id.cinemaListView);
                    cinemaListView.setAdapter(cinemaAdapter);

                    cinemaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent i= new Intent(CinemaListActivity.this,MovieListActivity.class);
                            i.putExtra("cinemaType",(String)view.getTag());
                            startActivity(i);
                        }
                    });

                } catch (Exception e) {
                    //TODO add some logic here to tell the user that the cinemas couldn't be retrieved
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
        }
    }
    
}