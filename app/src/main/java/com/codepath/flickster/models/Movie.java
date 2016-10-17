package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevinlau on 10/16/16.
 */

public class Movie {

    private static final String POSTER_PATH_PREFIX
        = "https://image.tmdb.org/t/p/w342";
    private static final String BACKDROP_PATH_PREFIX
            = "https://image.tmdb.org/t/p/w780";

    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getPosterPath() {
        return String.format("%s%s", POSTER_PATH_PREFIX, posterPath);
    }

    public String getBackdropPath() {
        return String.format("%s%s", BACKDROP_PATH_PREFIX, backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }
}
