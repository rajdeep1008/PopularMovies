package com.android.example.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rajdeep1008 on 9/3/16.
 */
public class Movie implements Parcelable{

    private String title;
    private String language;
    private String posterUrl;
    private String bannerUrl;
    private String overview;
    private String rating;
    private String releaseDate;

    public Movie(String title, String language, String posterUrl, String bannerUrl,
            String overview, String rating, String releaseDate){

        this.title = title;
        this.language = language;
        this.posterUrl = posterUrl;
        this.bannerUrl = bannerUrl;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.overview = overview;
    }

    private Movie(Parcel in) {
        title = in.readString();
        language = in.readString();
        posterUrl = in.readString();
        bannerUrl = in.readString();
        rating = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(language);
        parcel.writeString(posterUrl);
        parcel.writeString(bannerUrl);
        parcel.writeString(rating);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public String getTitle(){
        return title;
    }

    public String getImageUrl() {
        return posterUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getLanguage() {
        return language;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
