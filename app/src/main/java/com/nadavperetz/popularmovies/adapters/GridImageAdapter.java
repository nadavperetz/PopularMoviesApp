package com.nadavperetz.popularmovies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nadavperetz on 10/10/16.
 */

public class GridImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] moviesList = {""};

    public void updateArray(String[] moviesList){
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    public GridImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return this.moviesList.length;
    }

    public String getItem(int position) {
        return moviesList[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT,
                    500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext)
                .load(getUrl(position))
                .fit()
                .into(imageView);

        return imageView;
    }

    private String getUrl(int position) {
        try {
            JSONObject movie = new JSONObject(moviesList[position]);
            String path = movie.getString("backdrop_path");
            return "http://image.tmdb.org/t/p/w185/" + path;
        }
        catch (JSONException e){
            return null;
        }
    }

}