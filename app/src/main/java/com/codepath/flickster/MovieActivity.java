package com.codepath.flickster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.flickster.adapters.MovieArrayAdapter;
import com.codepath.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MovieActivity";

    private SwipeRefreshLayout swipeContainer;

    private ListView lvItems;
    private ArrayList<Movie> movies;
    private MovieArrayAdapter movieAdapter;

    private final String MOVIE_DB_URL
        = "https://api.themoviedb.org/3/movie/now_playing?api_key="
        + "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        setupSwipeContainer();

        lvItems = (ListView)findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        populateMovies();
    }

    public void populateMovies() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(MOVIE_DB_URL,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        JSONArray movieJsonResults = null;

                        try {
                            movieJsonResults
                                    = response.getJSONArray("results");
                            ArrayList<Movie> movieResults
                                    = Movie.fromJSONArray(movieJsonResults);
                            movies.clear();
                            movies.addAll(movieResults);
                            movieAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e(LOG_TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          String responseString,
                                          Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString,
                                        throwable);
                    }
                });
    }

    private void setupSwipeContainer() {
        SwipeRefreshLayout.OnRefreshListener swipeContainerRefreshListener
            = new SwipeRefreshLayout.OnRefreshListener() {
                  @Override
                  public void onRefresh() {
                      populateMovies();
                      swipeContainer.setRefreshing(false);
                  }
              };
        swipeContainer.setOnRefreshListener(swipeContainerRefreshListener);

        swipeContainer
            .setColorSchemeResources(android.R.color.holo_blue_bright);
    }
}
