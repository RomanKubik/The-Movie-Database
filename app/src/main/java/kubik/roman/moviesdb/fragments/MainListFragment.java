package kubik.roman.moviesdb.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.TmdbUrlBuilder;
import kubik.roman.moviesdb.adapters.MoviesListAdapter;
import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.movies_list.EndlessOnScrollListener;
import kubik.roman.moviesdb.models.movies_list.GenresList;
import kubik.roman.moviesdb.models.movies_list.Movie;
import kubik.roman.moviesdb.models.movies_list.MoviesList;

/**
 * Fragment for displaying main list
 */
public class MainListFragment extends BaseFragment implements Response.ErrorListener {

    private MoviesList mMoviesList;
    private GenresList mGenresList;

    private MoviesListAdapter mMoviesListAdapter;

    private View view;

    private RequestQueue queue;

    private List<Movie> mMovies = new ArrayList<>();
    private List<Genre> mGenres = new ArrayList<>();

    private int mLastPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getBaseActivity());

        getGenresList();
        getMoviesList(mLastPage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_list_fragment, null);

        initializeList();

        return view;
    }

    private void initializeList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_title);
        mMoviesListAdapter = new MoviesListAdapter(mMovies, mGenres, getActivity());
        recyclerView.setAdapter(mMoviesListAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        mMoviesListAdapter.SetOnItemClickListener(new MoviesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Movie movie = mMovies.get(position);

                MovieDetailsPagerFragment fragment = MovieDetailsPagerFragment.
                        newInstance(movie.getId());
                navigateTo(fragment);
                /*MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.
                        newInstance(movie.getId());
                navigateTo(movieDetailsFragment);*/
            }
        });
        recyclerView.setOnScrollListener(new EndlessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getMoviesList(mLastPage + 1);
            }
        });
    }

    private void getGenresList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TmdbUrlBuilder.getGenresListUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mGenresList = gson.fromJson(response, GenresList.class);
                mGenres.addAll(mGenresList.getGenres());
            }
        }, this);

        queue.add(stringRequest);
    }

    private void getMoviesList(int currentPage) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                TmdbUrlBuilder.getMoviesListTopRatedUrl(currentPage), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                mMoviesList = gson.fromJson(response, MoviesList.class);
                mLastPage = mMoviesList.getPage();
                updateListView(mMoviesList.getResults());
            }
        }, this);
        queue.add(stringRequest);
    }

    private void updateListView(List<Movie> movies){
         mMovies.addAll(movies);
         mMoviesListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        String json;

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


}
