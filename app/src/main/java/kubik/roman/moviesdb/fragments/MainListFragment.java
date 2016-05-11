package kubik.roman.moviesdb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
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

import kubik.roman.moviesdb.GsonGetRequest;
import kubik.roman.moviesdb.R;
import kubik.roman.moviesdb.TmdbUrlBuilder;
import kubik.roman.moviesdb.activities.MovieDetailsActivity;
import kubik.roman.moviesdb.adapters.MoviesListAdapter;
import kubik.roman.moviesdb.models.Genre;
import kubik.roman.moviesdb.models.movies_list.EndlessOnScrollListener;
import kubik.roman.moviesdb.models.movies_list.GenresList;
import kubik.roman.moviesdb.models.movies_list.Movie;
import kubik.roman.moviesdb.models.movies_list.MoviesList;

/**
 * Fragment for displaying list of something
 */
public class MainListFragment extends BaseFragment implements Response.ErrorListener {

    private static final String TYPE_TAG = "type";
    //Type of movies list 0- popular, 1- top rated, 2- upcoming, 3- now playing
    private int mType = 0;

    private MoviesList mMoviesList;

    private MoviesListAdapter mMoviesListAdapter;

    private View view;

    private RequestQueue queue;

    private List<Movie> mMovies = new ArrayList<>();
    private List<Genre> mGenres = new ArrayList<>();

    private int mLastPage = 1;
    private boolean isInitialized = false;

    public static MainListFragment newInstance(int type) {
        MainListFragment fragment = new MainListFragment();

        Bundle args = new Bundle();
        args.putInt(TYPE_TAG, type);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getBaseActivity());
        mType = getArguments().getInt(TYPE_TAG);

        getBaseActivity().mToolbar.setTitle(R.string.app_name);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_list_fragment, null);
        getGenresList();
        getMoviesList(mLastPage);
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
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra("movie_id",movie.getId());
                startActivity(intent);
//                MovieDetailsPagerFragment fragment = MovieDetailsPagerFragment.
//                        newInstance(movie.getId());
                //navigateTo(fragment, true);
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
        GsonGetRequest<GenresList> request = new GsonGetRequest<>(TmdbUrlBuilder.getGenresListUrl(),
                GenresList.class, null, new Response.Listener<GenresList>() {
            @Override
            public void onResponse(GenresList response) {
                mGenres = response.getGenres();
                /*if (mMoviesListAdapter != null)
                    mMoviesListAdapter.notifyDataSetChanged();*/
            }
        }, this);

        queue.add(request);
    }

    private void getMoviesList(int currentPage) {
        String url;
        Log.d(TYPE_TAG, String.valueOf(mType));
        switch (mType) {
            case 0:
                url = TmdbUrlBuilder.getMoviesListPopularUrl(currentPage);
                break;
            case 1:
                url = TmdbUrlBuilder.getMoviesListTopRatedUrl(currentPage);
                break;
            case 2:
                url = TmdbUrlBuilder.getMoviesListUpcomingUrl(currentPage);
                break;
            case 3:
                url = TmdbUrlBuilder.getMoviesListNowPlayingUrl(currentPage);
                break;
            default:
                url = TmdbUrlBuilder.getMoviesListPopularUrl(currentPage);
                break;
        }
        GsonGetRequest<MoviesList> request = new GsonGetRequest<>(url, MoviesList.class, null,
                new Response.Listener<MoviesList>() {
                    @Override
                    public void onResponse(MoviesList response) {
                        mMoviesList = response;
                        mLastPage = mMoviesList.getPage();
                        updateListView(mMoviesList.getResults());
                    }
                }, this);
        queue.add(request);
    }

    private void updateListView(List<Movie> movies) {
        mMovies.addAll(movies);
        if (!isInitialized) {
            initializeList();
            isInitialized = true;
        }
        mMoviesListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        String json;

        if(error instanceof NoConnectionError) {
            json = "No internet Access, Check your internet connection.";
            Toast.makeText(getActivity(), json, Toast.LENGTH_LONG).show();
            return;
        }

        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 200:
                    break;
                default:
                    json = new String(response.data);
                    json = trimMessage(json, "status_message");
                    if (json != null) showToast(json);
                    break;
            }
        }
    }

    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


}
