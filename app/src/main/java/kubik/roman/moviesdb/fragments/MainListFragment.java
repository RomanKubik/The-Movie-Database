package kubik.roman.moviesdb.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import kubik.roman.moviesdb.HttpConnectionManager;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.adapters.MovieListAdapter;
import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.Movie;
import kubik.roman.moviesdb.models.MovieResponse;
import kubik.roman.moviesdb.models.MoviesList;

/**
 * Fragment for displaying main list
 */
public class MainListFragment extends BaseFragment implements HttpConnectionManager.OnRespondListener, AdapterView.OnItemClickListener {

    public static final String REQUESTED_GENRE_LIST = "genre/movie/list";
    public static final String GENRES = "genres";

    private HttpConnectionManager mHttpConnectionManager;
    private MoviesList mMoviesList;
    private ArrayList<Genre> mGenresList;

    private Context mContext;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_list_fragment, null);
        mContext = container.getContext();
        try {
            mHttpConnectionManager = new HttpConnectionManager(mContext);
            mHttpConnectionManager.setOnRespondListener(this);

            getGenresList();
            showMoviesList();
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.d("MainFragment", e.toString());
        }
        return view;
    }

    public void thisWillBeCalledOnBackPressed() {
        showToast("This method is invoked on back action of USER");
    }

    private void getGenresList() throws InterruptedException, ExecutionException, JSONException {

        mGenresList = new ArrayList<>();

        mHttpConnectionManager.GET(REQUESTED_GENRE_LIST);
    }

    private void setGenresList(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray jsonArray = jsonObject.getJSONArray(GENRES);

        for (int i = 0; i < jsonArray.length(); i++) {
            Genre genre = new Genre();

            genre.setGenreFromJson(jsonArray.getString(i));
            mGenresList.add(genre);
        }

    }

    private void showMoviesList() throws ExecutionException, InterruptedException, JSONException {

        mMoviesList = new MoviesList();

        mHttpConnectionManager.GET(MoviesList.REQUESTED);

        showList();

    }

    private void showList() {
        MovieListAdapter adapter = new MovieListAdapter(mMoviesList, mGenresList, mContext);
        ListView listView = (ListView) view.findViewById(R.id.lvTitle);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }


    @Override
    public void onRespond(String respond, String requested) throws JSONException,
            ExecutionException, InterruptedException {

        switch (requested) {
            case MoviesList.REQUESTED:
                mMoviesList.setMoviesListFromJson(respond);
                break;
            case REQUESTED_GENRE_LIST:
                setGenresList(respond);
                break;
            default:
                break;
        }

    }


    @Override
    public void onError(String error) {
        //Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
        showToast(error);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = (Movie) parent.getItemAtPosition(position);
        Log.d("MOVIE :: ", movie.toString());

        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie.getId());
        navigateTo(movieDetailsFragment);
    }

}
