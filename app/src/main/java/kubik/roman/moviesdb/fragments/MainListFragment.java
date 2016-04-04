package kubik.roman.moviesdb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.adapters.MovieListAdapter;
import kubik.roman.moviesdb.models.movies_list.GenresList;
import kubik.roman.moviesdb.models.movies_list.Movie;
import kubik.roman.moviesdb.models.movies_list.MoviesList;

/**
 * Fragment for displaying main list
 */
public class MainListFragment extends BaseFragment implements Response.ErrorListener, AdapterView.OnItemClickListener {

    public static final String GENRES_LIST_URL = "http://api.themoviedb.org/3/genre/movie/list?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";
    public static final String MOVIES_LIST_TOP_RATE_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=f3fe610fbf5ef2e3b5e06d701a2ba5a3";


    private MoviesList mMoviesList;
    private GenresList mGenresList;

    private View view;

    private RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_list_fragment, null);

        queue = Volley.newRequestQueue(getBaseActivity());

        getGenresList();
        getMoviesList();

        return view;
    }

    private void getGenresList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                GENRES_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mGenresList = gson.fromJson(response, GenresList.class);
            }
        }, this);

        queue.add(stringRequest);
    }

    private void getMoviesList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                MOVIES_LIST_TOP_RATE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mMoviesList = gson.fromJson(response, MoviesList.class);
                showMoviesList();
            }
        }, this);
        queue.add(stringRequest);
    }

    private void showMoviesList() {
        MovieListAdapter adapter = new MovieListAdapter(mMoviesList, mGenresList, getBaseActivity());
        ListView listView = (ListView) view.findViewById(R.id.lvTitle);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String json = null;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 200:
                    break;
                default:
                    json = new String(response.data);
                    json = trimMessage(json, "status_message");
                    if(json != null) showToast(json);
                    break;
            }
        }
    }

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = (Movie) parent.getItemAtPosition(position);
        Log.d("MOVIE :: ", movie.toString());

        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(movie.getId());
        navigateTo(movieDetailsFragment);
    }

}
