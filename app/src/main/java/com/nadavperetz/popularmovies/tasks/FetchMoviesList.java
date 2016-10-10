package com.nadavperetz.popularmovies.tasks;

import android.os.AsyncTask;

import com.nadavperetz.popularmovies.adapters.GridImageAdapter;
import com.nadavperetz.popularmovies.helpers.ApiMovieDbUrlCreator;
import com.nadavperetz.popularmovies.support.WebClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by nadavperetz on 10/10/16.
 */

public class FetchMoviesList extends AsyncTask<String, String, String> {
    private static final String LOG_TAG = "FetchMovies";
    private String sort_type;
    public GridImageAdapter mMoviesAdapter;

    public FetchMoviesList(String sort_type, GridImageAdapter mMoviesAdapter){
        this.sort_type = sort_type;
        this.mMoviesAdapter = mMoviesAdapter;
    }

    @Override
    protected String doInBackground(String... strings) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        URL url = new ApiMovieDbUrlCreator().uri_builder(this.sort_type);
        WebClient webClient = new WebClient();
        return webClient.GetData(url);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            this.mMoviesAdapter.updateArray(getMovieList(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected String[] getMovieList(String result) throws JSONException {
        JSONObject json_movies = new JSONObject(result);
        JSONArray moviesArray = json_movies.getJSONArray("results");
        String[] moviesList = new String[moviesArray.length()];
        for (int i = 0; i < moviesArray.length(); i++){
            JSONObject movie = moviesArray.getJSONObject(i);
            moviesList[i] = movie.toString();
            //Log.v(LOG_TAG, moviesList[i]);
        }
        return moviesList;
    }
}
