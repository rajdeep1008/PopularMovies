package com.android.example.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

//        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_layout, null);
            mImageView = (ImageView) convertView.findViewById(R.id.movie_image);
            mTextView = (TextView) convertView.findViewById(R.id.movie_text);

        }
        Movie currentMovie = moviesList.get(position);

//        holder.mTextView.setText(moviesList.get(position).getTitle());
        mTextView.setText(currentMovie.getTitle());
        Picasso.with(mContext).load(currentMovie.getImageUrl()).placeholder(R.mipmap.ic_launcher).into(mImageView);

        return convertView;
    }
}

//class ViewHolder {
//   public TextView mTextView;
//    public ImageView mImageView;
//
//    public ViewHolder(View base) {
//        mTextView = (TextView) base.findViewById(R.id.movie_text);
//        mImageView = (ImageView) base.findViewById(R.id.movie_image);
//    }
//}
