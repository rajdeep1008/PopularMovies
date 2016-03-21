package com.android.example.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rajdeep1008 on 10/3/16.
 */
public class MoviesAdapter extends BaseAdapter {

    List<Movie> moviesList;
    Context mContext;
    public TextView mTextView;
    public ImageView mImageView;
    public LinearLayout background;

    public MoviesAdapter(Context context, List<Movie> list){
        super();
        this.moviesList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int i) {
        return moviesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.grid_item_layout, null);
        mImageView = (ImageView) convertView.findViewById(R.id.movie_image);
        mTextView = (TextView) convertView.findViewById(R.id.movie_text);
        background = (LinearLayout) convertView.findViewById(R.id.grid_background);

        final Movie currentMovie = moviesList.get(position);

        mTextView.setText(currentMovie.getTitle());
        Picasso.with(mContext)
                .load(currentMovie.getImageUrl())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mImageView.setImageBitmap(bitmap);

                        if(bitmap != null && !bitmap.isRecycled()) {
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {
                                    int deflt = 0x000000;
                                    int vibrant = palette.getVibrantColor(deflt);
//                                    int vibrantLight = palette.getLightVibrantColor(deflt);
//                                    int vibrantDark = palette.getDarkVibrantColor(deflt);
//                                    int muted = palette.getMutedColor(deflt);
//                                    int mutedLight = palette.getLightMutedColor(deflt);
//                                    int mutedDark = palette.getDarkMutedColor(deflt);
//                                    Log.e("URL", currentMovie.getImageUrl());
                                    background.setBackgroundColor(vibrant);
                                }
                            });
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        mImageView.setImageResource(R.drawable.placeholder);
                    }
                });

        return convertView;
    }
}

