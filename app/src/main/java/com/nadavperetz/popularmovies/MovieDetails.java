package com.nadavperetz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {
    private String LOG_TAG = "MovieDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle extras = getIntent().getExtras();
        String movieDetails = null;
        if (extras != null) {
            movieDetails = extras.getString(Intent.EXTRA_TEXT);
        }
        if (movieDetails != null){
            insertData(movieDetails);
        }
    }

    private void insertData(String movieDetails) {
        try {
            JSONObject jsonObject = new JSONObject(movieDetails);
            String original_title = jsonObject.getString("original_title");

            String poster_path = jsonObject.getString("backdrop_path");
            String overview = jsonObject.getString("overview");
            String release_date = jsonObject.getString("release_date");
            String vote_average = jsonObject.getString("vote_average");

            TextView movie_title = (TextView) findViewById(R.id.movie_title);
            movie_title.setText(original_title);

            TextView text_overview = (TextView) findViewById(R.id.plot_synopsis);
            text_overview.setText(overview);

            TextView text_vote_average = (TextView) findViewById(R.id.vote_average_number);
            text_vote_average.setText(vote_average);


            TextView text_release_date = (TextView) findViewById(R.id.release_date);
            text_release_date.setText(release_date);

            ImageView movie_image = (ImageView) findViewById(R.id.movie_image);


            Picasso.with(getApplicationContext())
                    .load("http://image.tmdb.org/t/p/w185/" + poster_path)
                    .centerCrop()
                    .resize(600,  800)
                    .into(movie_image);

        }catch (JSONException e){
            Log.e(LOG_TAG, e.toString());
        }


    }
}
