package com.nadavperetz.popularmovies.helpers;

import android.net.Uri;
import android.util.Log;

import com.nadavperetz.popularmovies.secret_keys.SecretKeys;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nadavperetz on 10/10/16.
 */

public class ApiMovieDbUrlCreator {


    public URL uri_builder(String sort_type){
        URL url;
        Uri.Builder url_path = new Uri.Builder();
        url_path.scheme("https");
        url_path.authority("api.themoviedb.org");
        url_path.path("3/movie/"+sort_type);
        url_path.appendQueryParameter("api_key", SecretKeys.ApiMovieDbUrlKey);
        try {
            url = new URL(url_path.toString());
            Log.v(getClass().toString(), url.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
