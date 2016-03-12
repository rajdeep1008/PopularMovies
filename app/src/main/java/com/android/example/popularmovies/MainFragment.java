package com.android.example.popularmovies;


import android.app.ProgressDialog;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    GridView mGridView;
    List<Movie> moviesList;
    ProgressDialog mProgressDialog;
    MoviesAdapter adapter;

    private final String TAG = MainActivity.class.getSimpleName();

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchMovies().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView) view.findViewById(R.id.movie_grid);
        return view;
    }

    public class FetchMovies extends AsyncTask<Void, Void, Integer>{

        private final String TAG = FetchMovies.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Downloading");
            mProgressDialog.setMessage("Loading movies...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;
            String response = "";
            URL url;
            int result = 0;     // zero denotes failure initially
            final String APIID_PARAM = "api_key";

            Uri builtUri = Uri.parse(Constants.BASE_URL).buildUpon()
                    .appendQueryParameter(APIID_PARAM, Constants.apiKey)
                    .build();
            try {
                url = new URL(builtUri.toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null){
                    Log.e(TAG, "inputstream is null");
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    Log.e(TAG, "buffer length is zero");
                }

                response = buffer.toString();

                if(response.length() >= 0){
                    result = 1;
                    parseMovieDetails(response);
                }

            } catch (MalformedURLException e) {
                Log.e(TAG, "" + e);

            } catch (IOException e) {
                Log.e(TAG, "" + e);
            } finally {

                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
                if(bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "" + e);
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result == 1) {
                adapter = new MoviesAdapter(getActivity(), moviesList);
                mGridView.setAdapter(adapter);
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), "successful", Toast.LENGTH_LONG).show();
            } else {
                mProgressDialog.dismiss();
                Toast.makeText(getActivity(), "Download failed", Toast.LENGTH_LONG).show();
            }
        }

        public void parseMovieDetails(String response){
            try {
                JSONObject object = new JSONObject(response);
                JSONArray results = object.optJSONArray("results");
                String title, language, posterUrl, bannerUrl, overview, rating, releaseDate;
                moviesList = new ArrayList<>(results.length());

                for(int i = 0; i < results.length(); i++){
                    JSONObject singleMovie = results.optJSONObject(i);
                    title = singleMovie.optString("original_title");
                    language = singleMovie.optString("original_language");
                    posterUrl = Constants.IMAGE_URL + singleMovie.optString("poster_path");
                    bannerUrl = Constants.IMAGE_URL + singleMovie.optString("backdrop_path");;
                    overview = singleMovie.optString( "overview");
                    rating = singleMovie.optString("vote_average");
                    releaseDate = singleMovie.optString("release_date");

                    Movie tempMovie = new Movie(title, language, posterUrl, bannerUrl,
                            overview, rating, releaseDate);
                    moviesList.add(tempMovie);
                }

            } catch (JSONException e) {
                Log.e(TAG, "" + e);
            }
        }
    }
}
