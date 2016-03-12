package com.android.example.popularmovies;

/**
 * Created by rajdeep1008 on 9/3/16.
 */
public class Movie {

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
