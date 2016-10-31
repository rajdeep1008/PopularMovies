package com.android.example.popularmovies.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.example.popularmovies.ApiClient;
import com.android.example.popularmovies.ApiInterface;
import com.android.example.popularmovies.Constants;
import com.android.example.popularmovies.Activities.DetailsActivity;
import com.android.example.popularmovies.Activities.MainActivity;
import com.android.example.popularmovies.Models.Movie;
import com.android.example.popularmovies.Adapters.MoviesAdapter;
import com.android.example.popularmovies.Models.MovieNew;
import com.android.example.popularmovies.Models.MovieResponse;
import com.android.example.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private GridView mGridView;
    private List<Movie> moviesList;
    private ProgressDialog mProgressDialog;
    private MoviesAdapter adapter;

    private final String TAG = MainActivity.class.getSimpleName();

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(Constants.apiKey);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieNew> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        moviesList.clear();
//        adapter.notifyDataSetChanged();
//        new FetchMovies().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

//        moviesList = new ArrayList<>();
//        adapter = new MoviesAdapter(getActivity(), moviesList);
//        mGridView = (GridView) view.findViewById(R.id.movie_grid);
//        mGridView.setAdapter(adapter);
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                intent.putExtra("movie", moviesList.get(i));
//                startActivity(intent);
//            }
//        });
        return view;
    }

//    public class FetchMovies extends AsyncTask<Void, Void, Integer>{
//
//        private final String TAG = FetchMovies.class.getSimpleName();
//
//        @Override
//        protected void onPreExecute() {
//            mProgressDialog = new ProgressDialog(getActivity());
//            mProgressDialog.setTitle("Downloading");
//            mProgressDialog.setMessage("Loading movies...");
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.setIndeterminate(true);
//            mProgressDialog.show();
//        }
//
//        @Override
//        protected Integer doInBackground(Void... voids) {
//
//            HttpURLConnection httpURLConnection = null;
//            BufferedReader bufferedReader = null;
//            String response = "";
//            URL url;
//            int result = 0;     // zero denotes failure initially
//            final String APIID_PARAM = "api_key";
//            final String SORT_PARAM = "sort_by";
//
//            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String sortType = preference.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default));
//
//            Uri builtUri = Uri.parse(Constants.BASE_URL).buildUpon()
//                    .appendQueryParameter(APIID_PARAM, Constants.apiKey)
//                    .appendQueryParameter(SORT_PARAM, sortType + ".desc")
//                    .build();
//            try {
//                url = new URL(builtUri.toString());
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("GET");
//                httpURLConnection.connect();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//
//                if(inputStream == null){
//                    Log.e(TAG, "inputstream is null");
//                }
//
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line = "";
//
//                while((line = bufferedReader.readLine()) != null){
//                    buffer.append(line + "\n");
//                }
//
//                if(buffer.length() == 0){
//                    Log.e(TAG, "buffer length is zero");
//                }
//
//                response = buffer.toString();
//
//                if(response.length() >= 0){
//                    result = 1;
//                    parseMovieDetails(response);
//                }
//
//            } catch (MalformedURLException e) {
//                Log.e(TAG, "" + e);
//
//            } catch (IOException e) {
//                Log.e(TAG, "" + e);
//            } finally {
//
//                if(httpURLConnection != null){
//                    httpURLConnection.disconnect();
//                }
//                if(bufferedReader != null){
//                    try {
//                        bufferedReader.close();
//                    } catch (IOException e) {
//                        Log.e(TAG, "" + e);
//                    }
//                }
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(Integer result) {
//            super.onPostExecute(result);
//
//            if (result == 1) {
//                adapter = new MoviesAdapter(getActivity(), moviesList);
//                mGridView.setAdapter(adapter);
//                mProgressDialog.dismiss();
//            } else {
//                mProgressDialog.dismiss();
//                Toast.makeText(getActivity(), "Download failed", Toast.LENGTH_LONG).show();
//            }
//        }
//
//        public void parseMovieDetails(String response){
//            try {
//                JSONObject object = new JSONObject(response);
//                JSONArray results = object.optJSONArray("results");
//                String title, language, posterUrl, bannerUrl, overview, rating, releaseDate;
//                moviesList = new ArrayList<>(results.length());
//
//                for(int i = 0; i < results.length(); i++){
//                    JSONObject singleMovie = results.optJSONObject(i);
//                    title = singleMovie.optString("original_title");
//                    language = singleMovie.optString("original_language");
//                    posterUrl = Constants.IMAGE_URL + singleMovie.optString("poster_path");
//                    bannerUrl = Constants.IMAGE_URL + singleMovie.optString("backdrop_path");;
//                    overview = singleMovie.optString( "overview");
//                    rating = singleMovie.optString("vote_average");
//                    releaseDate = singleMovie.optString("release_date");
//
//                    Movie tempMovie = new Movie(title, language, posterUrl, bannerUrl,
//                            overview, rating, releaseDate);
//                    moviesList.add(tempMovie);
////                    Log.e(TAG, tempMovie.getImageUrl());
//                }
//
//            } catch (JSONException e) {
//                Log.e(TAG, "" + e);
//            }
//        }
//    }
}
