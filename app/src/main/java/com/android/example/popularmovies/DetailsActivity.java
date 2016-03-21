package com.android.example.popularmovies;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    Movie movie;
    @Bind(R.id.toolbar_image) ImageView toolbarImage;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.poster) ImageView poster;
    @Bind(R.id.overview) TextView overview;
    @Bind(R.id.movie_title) TextView title;
    @Bind(R.id.movie_date) TextView releaseDate;
    @Bind(R.id.movie_rating) TextView rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getParcelableExtra("movie");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this)
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.placeholder)
                .into(poster);

        Picasso.with(this)
                .load(movie.getBannerUrl())
                .placeholder(R.drawable.placeholder)
                .into(toolbarImage);

        overview.setText(movie.getOverview());
        title.setText(movie.getTitle());
        releaseDate.setText("Release Date : " + movie.getReleaseDate());
        rating.setText("Average Rating : " + movie.getRating());

        CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.white,getTheme()));
            collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        else {
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        }
        collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor("#FFFFFF"));
    }
}
