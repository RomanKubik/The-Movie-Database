package kubik.roman.moviesdb.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * List of movies
 */
public class MoviesList {

    public static final String REQUESTED = "movie/top_rated";

    public static final String PAGE = "page";
    public static final String RESULTS = "results";
    public static final String TOTAL_RESULTS = "total_results";
    public static final String TOTAL_PAGES = "total_pages";

    private int mPage;
    private ArrayList<Movie> mMoviesList;
    private int mTotalResults;
    private int mTotalPages;

    public void setMoviesListFromJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);

        mPage = jsonObject.getInt(PAGE);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
        mMoviesList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Movie movie = new Movie();
            movie.setMovieFromJson(jsonArray.getString(i));
            mMoviesList.add(movie);
        }
        mTotalResults = jsonObject.getInt(TOTAL_RESULTS);
        mTotalPages = jsonObject.getInt(TOTAL_PAGES);
    }

    public int getPage() {
        return mPage;
    }

    public ArrayList<Movie> getMoviesList() {
        return mMoviesList;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public int getTotalPages() {
        return mTotalPages;
    }
}
