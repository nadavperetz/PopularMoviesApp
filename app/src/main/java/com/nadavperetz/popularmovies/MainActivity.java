package com.nadavperetz.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nadavperetz.popularmovies.adapters.GridImageAdapter;
import com.nadavperetz.popularmovies.tasks.FetchMoviesList;

public class MainActivity extends AppCompatActivity {
    private String sort_type = "sort_type";
    private String sort_by_popularity = "popular";
    private String sort_by_rate = "top_rated";
    private SharedPreferences sharedPrefs;
    private GridImageAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        mMoviesAdapter = new GridImageAdapter (this.getApplicationContext());

        GridView gridview = (GridView) findViewById(R.id.MoviesGridview);


        gridview.setAdapter(mMoviesAdapter);
        updateMovieList();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (checkConnectivity()) {
                    String movieList = mMoviesAdapter.getItem(position);
                    Intent intent = new Intent(getApplicationContext(), MovieDetails.class)
                            .putExtra(Intent.EXTRA_TEXT, movieList);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences.Editor editor = sharedPrefs.edit();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_by_popularity) {
            editor.putString(sort_type, sort_by_popularity);
            editor.apply();
            updateMovieList();
            return true;
        }

        if (id == R.id.action_sort_by_rate) {
            editor.putString(sort_type, sort_by_rate);
            editor.apply();
            updateMovieList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMovieList(){
        if (checkConnectivity()) {
            FetchMoviesList fetchMoviesList = new FetchMoviesList(
                    sharedPrefs.getString(sort_type, sort_by_popularity), mMoviesAdapter);
            fetchMoviesList.execute();
        }
        else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkConnectivity(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());

    }
}

